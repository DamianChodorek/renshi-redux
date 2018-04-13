package com.damianchodorek.renshi.store.state

import io.reactivex.Flowable

/**
 * Container of [State].
 * @param STATE the type of state.
 * @author Damian Chodorek
 */
interface StateContainer<STATE : State> {

    /**
     * State instance.
     */
    var state: STATE
    /**
     * Flowable that emits [state] when it is set to new instance.
     */
    val stateChanges: Flowable<STATE>
}