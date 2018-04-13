package com.damianchodorek.renshi.utils.integrationImpl

import com.damianchodorek.renshi.store.state.State

/**
 * @author Damian Chodorek
 */
internal data class StateTestImpl(
        val testProperty: Boolean = false,
        override val lastActionMark: Any? = null
) : State {

    override fun clone(lastActionMark: Any?) = copy(lastActionMark = lastActionMark)
}