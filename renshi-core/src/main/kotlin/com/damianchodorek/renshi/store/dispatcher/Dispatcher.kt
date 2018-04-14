package com.damianchodorek.renshi.store.dispatcher

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.renshi.store.state.State
import com.damianchodorek.renshi.store.state.StateContainer
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers.from
import java.util.concurrent.Executors.newSingleThreadExecutor

/**
 * Responsible for calling [Reducer.reduce] on currently set reducer.
 * @param ACTION the subtype of action to reduce.
 * @author Damian Chodorek
 */
interface Dispatcher<in ACTION : Action> {

    /**
     * Calls [Reducer.reduce] with current stores state and [action]. By default it operates on
     * single threaded scheduler so every call is queued and synchronized. To override default
     * behaviour modify [Dispatcher.schedulerProvider].
     * @param action action to dispatch.
     * @return completable that completes when dispatch is finished.
     */
    fun dispatch(action: ACTION): Completable

    @Suppress("UNCHECKED_CAST")
    companion object {

        /**
         * Provides scheduler for every dispatcher. By default it provides scheduler that operates
         * on single thread so every call to [dispatch] is queued and synchronized.
         * It's public so you can override it in your tests for example:
         * ```
         * Dispatcher.schedulerProvider = { Schedulers.trampoline() }
         * ```
         */
        var schedulerProvider: () -> Scheduler = createDefaultSchedulerProvider()

        /**
         * Sets [schedulerProvider] to default value.
         */
        fun resetSchedulerProvider() {
            schedulerProvider = createDefaultSchedulerProvider()
        }

        private fun createDefaultSchedulerProvider() = { from(newSingleThreadExecutor()) }

        /**
         * Creates new dispatcher that updates state using proper reducer.
         * @param stateContainer olds current store state.
         * @param reducer top level reducer that should handle all [dispatch] calls.
         * @return new [Dispatcher] instance.
         */
        fun <STATE : State, ACTION : Action> create(
                stateContainer: StateContainer<STATE>,
                reducer: Reducer<ACTION, STATE>
        ): Dispatcher<ACTION> =
                object : Dispatcher<ACTION> {

                    private val scheduler = schedulerProvider()

                    override fun dispatch(action: ACTION) = Single
                            .just(action)
                            .flatMapCompletable { itAction ->
                                reducer
                                        .reduce(itAction, stateContainer.state)
                                        .map { it.clone(lastActionMark = itAction.actionMark) }
                                        .doOnSuccess { stateContainer.state = it as STATE }
                                        .filter { itAction.singleTime }
                                        .flatMapCompletable {
                                            Completable.fromAction {
                                                stateContainer.state = stateContainer.state.clone(lastActionMark = null) as STATE
                                            }
                                        }
                            }.subscribeOn(scheduler)

                }
    }
}