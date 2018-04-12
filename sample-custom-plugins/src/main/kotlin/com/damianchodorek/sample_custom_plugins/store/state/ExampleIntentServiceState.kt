package com.damianchodorek.sample_custom_plugins.store.state

import com.damianchodorek.renshi.store.state.State

data class ExampleIntentServiceState(
        override val lastActionMark: Any?
) : State {

    override fun clone(lastActionMark: Any?) = copy(lastActionMark = lastActionMark)
}