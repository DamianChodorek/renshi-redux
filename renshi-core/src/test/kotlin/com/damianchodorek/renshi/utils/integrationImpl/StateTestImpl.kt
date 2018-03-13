package com.damianchodorek.renshi.utils.integrationImpl

import com.damianchodorek.renshi.store.state.State

internal data class StateTestImpl(
        val testProperty: Boolean = false,
        override val lastActionMark: Any? = null
) : State {

    override fun clone(lastActionRenderMark: Any?) = copy(lastActionMark = lastActionRenderMark)
}