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

/**
 * Base implementation of [Store].
 * @param STATE the subtype of [State] that store holds.
 * @author Damian Chodorek
 */
abstract class BaseStore<STATE : State> : Store<STATE> {

    /**
     * Initial state for store.
     */
    protected abstract val initialState: STATE
    /**
     * Object that updates store state. Usually this is [com.damianchodorek.renshi.store.reducer.CompositeReducer].
     */
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