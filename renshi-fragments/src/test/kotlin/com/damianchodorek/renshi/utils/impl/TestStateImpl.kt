package com.damianchodorek.renshi.utils.impl

import com.damianchodorek.renshi.store.state.State

data class TestStateImpl(
        override val lastActionRenderMark: Any? = null
) : State {

    override fun clone(lastActionRenderMark: Any?): State = copy(lastActionRenderMark = lastActionRenderMark)
}