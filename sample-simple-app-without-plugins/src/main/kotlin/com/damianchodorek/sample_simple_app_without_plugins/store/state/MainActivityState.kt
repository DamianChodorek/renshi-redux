package com.damianchodorek.sample_simple_app_without_plugins.store.state

import com.damianchodorek.renshi.store.state.State

/**
 * Describes state of [com.damianchodorek.sample_simple_app_without_plugins.view.MainActivity] and its subviews.
 * @param apiCallsCount count of api calls in background.
 * @param loading indicates if view is in loading state.
 */
data class MainActivityState(
        val apiCallsCount: Int = 0,
        val loading: Boolean = false,
        override val lastActionMark: Any? = null
) : State {

    override fun clone(lastActionMark: Any?) = copy(lastActionMark = lastActionMark)
}