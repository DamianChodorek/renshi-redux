package com.damianchodorek.sample_custom_plugins.store.state

import com.damianchodorek.renshi.store.state.State

/**
 * State of the service. It's very simple to not overcomplicate the example.
 * @author Damian Chodorek
 */
data class ExampleIntentServiceState(
        override val lastActionMark: Any?
) : State {

    override fun clone(lastActionMark: Any?) = copy(lastActionMark = lastActionMark)
}