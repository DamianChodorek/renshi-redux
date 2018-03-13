package com.damianchodorek.renshiredux.store.state

import com.damianchodorek.renshi.store.state.State

data class MainActivityState(
        val apiCallsCount: Int,
        override val lastActionMark: Any?
) : State {

    override fun clone(lastActionRenderMark: Any?): State {
        throw NotImplementedError()
    }
}