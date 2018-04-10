package com.damianchodorek.renshi.store.dispatcher

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.renshi.store.state.State
import com.damianchodorek.renshi.store.state.StateContainer
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

/**
 * Responsible for calling [Reducer.reduce] on currently set reducer.
 * @param ACTION the subtype of action to reduce.
 */
interface Dispatcher<in ACTION : Action> {

    /**
     * Calls [Reducer.reduce] with current store state and [action].
     * @param action action to dispatch.
     */
    fun dispatch(action: ACTION): Completable

    @Suppress("UNCHECKED_CAST")
    companion object {

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

                    override fun dispatch(action: ACTION) =
                            reducer
                                    .reduce(action, stateContainer.state)
                                    .map { it.clone(lastActionMark = action.actionMark) }
                                    .doOnSuccess { stateContainer.state = it as STATE }
                                    .filter { action.singleTime }
                                    .flatMapCompletable {
                                        Completable.fromAction {
                                            stateContainer.state = stateContainer.state.clone(lastActionMark = null) as STATE
                                        }
                                    }
                                    .subscribeOn(Schedulers.computation())

                }
    }
}