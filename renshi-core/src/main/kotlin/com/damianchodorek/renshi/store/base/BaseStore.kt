package com.damianchodorek.renshi.store.base

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.dispatcher.Dispatcher
import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.renshi.store.state.State
import com.damianchodorek.renshi.store.state.StateContainer
import com.damianchodorek.renshi.store.state.base.BaseStateContainer
import io.reactivex.Completable
import io.reactivex.Flowable

abstract class BaseStore<STATE : State> : Store<STATE> {

    protected abstract val initialState: STATE
    protected abstract val stateReducer: Reducer<Action, STATE>

    private val stateContainer: StateContainer<STATE> by lazy { BaseStateContainer(initialState) }
    private val dispatcher: Dispatcher<Action> by lazy {
        Dispatcher.create(stateContainer, stateReducer)
    }

    override val state: STATE
        get() = stateContainer.state

    override val stateChanges: Flowable<STATE>
        get() = stateContainer.stateChanges

    override fun dispatch(action: Action): Completable = dispatcher.dispatch(action)
}