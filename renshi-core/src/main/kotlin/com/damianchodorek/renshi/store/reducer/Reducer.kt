package com.damianchodorek.renshi.store.reducer

import com.damianchodorek.renshi.action.Action
import io.reactivex.Single

/**
 * Specifies how the state changes in response to actions sent to the store.
 * Read more at: https://redux.js.org/basics/reducers
 * @param ACTION the subtype of action that reducer can use.
 */
interface Reducer<in ACTION : Action, STATE> {

    /**
     * Plain function with no side effects. It takes action and current state and returns new state.
     * @param action action that represents intent.
     * @param state current state in store.
     * @return Single that emits new state instance and completes. Note that reducer MUST return modified
     * copy of old state, because state object is immutable.
     */
    fun reduce(action: ACTION, state: STATE): Single<STATE>
}