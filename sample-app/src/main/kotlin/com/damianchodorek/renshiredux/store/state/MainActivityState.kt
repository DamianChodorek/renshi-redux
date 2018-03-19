package com.damianchodorek.renshiredux.store.state

import com.damianchodorek.renshi.store.state.State

data class MainActivityState(
        val apiCallsCount: Int = 0,
        override val lastActionMark: Any? = null
) : State {

    override fun clone(lastActionMark: Any?) = copy(lastActionMark = lastActionMark)
}