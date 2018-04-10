package com.damianchodorek.renshi.store

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.state.State
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * The store has the following responsibilities:
 * - Holds application state;
 * - Allows access to state;
 * - Allows state to be updated via dispatch(action);
 * - Emits state changes;
 * Read more at: https://redux.js.org/basics/store
 * @param STATE subclass of [State] that is stored in store.
 */
interface Store<STATE : State> {

    /**
     * Immutable state object.
     */
    val state: STATE
    /**
     * Flowable that emits new [state] every time it changes.
     */
    val stateChanges: Flowable<STATE>

    /**
     * Updates [state] by using registered reducers ([com.damianchodorek.renshi.store.reducer.Reducer]).
     * @param action action that fires proper reducer.
     * @return Completable that completes when [action] is processed by reducer and [state] updated.
     */
    fun dispatch(action: Action): Completable
}