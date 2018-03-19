package com.damianchodorek.renshi.utils.impl

import com.damianchodorek.renshi.store.state.State

data class StateTestImpl(
        override val lastActionMark: Any? = null
) : State {

    override fun clone(lastActionMark: Any?): State = copy(lastActionMark = lastActionMark)
}