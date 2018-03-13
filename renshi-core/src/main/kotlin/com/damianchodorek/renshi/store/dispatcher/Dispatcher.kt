package com.damianchodorek.renshi.store.dispatcher

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.renshi.store.state.State
import com.damianchodorek.renshi.store.state.StateContainer
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

interface Dispatcher<in ACTION : Action> {

    fun dispatch(action: ACTION): Completable

    @Suppress("UNCHECKED_CAST")
    companion object {

        fun <STATE : State, ACTION : Action> create(
                stateContainer: StateContainer<STATE>,
                reducer: Reducer<ACTION, STATE>
        ): Dispatcher<ACTION> =
                object : Dispatcher<ACTION> {

                    override fun dispatch(action: ACTION) =
                            reducer
                                    .reduce(action, stateContainer.state)
                                    .map { it.clone(lastActionRenderMark = action.actionMark) }
                                    .doOnSuccess { stateContainer.state = it as STATE }
                                    .filter { action.singleTime }
                                    .flatMapCompletable {
                                        Completable.fromAction {
                                            stateContainer.state = stateContainer.state.clone(lastActionRenderMark = null) as STATE
                                        }
                                    }
                                    .subscribeOn(Schedulers.computation())

                }
    }
}