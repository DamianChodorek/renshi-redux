package com.damianchodorek.renshi.store.state.base

import com.damianchodorek.renshi.store.state.State
import com.damianchodorek.renshi.store.state.StateContainer
import io.reactivex.processors.BehaviorProcessor
import kotlin.properties.Delegates

/**
 * Base implementation of [StateContainer].
 * @param STATE the type of state.
 * @author Damian Chodorek
 */
class BaseStateContainer<STATE : State>(
        initialState: STATE
) : StateContainer<STATE> {

    private val stateChangesProcessor = BehaviorProcessor.create<STATE>()!!
    override val stateChanges = stateChangesProcessor.distinctUntilChanged()!!
    override var state: STATE by Delegates.observable(initialState) { _, _, newVal ->
        publishNewState(newVal)
    }

    private fun publishNewState(state: STATE) = stateChangesProcessor.onNext(state)

    init {
        publishNewState(state)
    }
}