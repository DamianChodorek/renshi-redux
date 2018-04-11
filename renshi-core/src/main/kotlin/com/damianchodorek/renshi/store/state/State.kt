package com.damianchodorek.renshi.store.state

/**
 * Represents current state of [com.damianchodorek.renshi.store.StoreOwner].
 * Usually you implement this interface by using data classes.
 */
interface State {

    /**
     * Optional helper field that contains mark ([com.damianchodorek.renshi.action.Action.actionMark])
     * of last dispatched action to help you identify what happened to state.
     */
    val lastActionMark: Any?

    /**
     * Copies state. Typically you use copy method from data class.
     * ```
     * override fun clone(lastActionMark: Any?) = copy(lastActionMark = lastActionMark)
     * ```
     * @param lastActionMark action mark of last dispatched action.
     * @return copy of the state with new [lastActionMark].
     */
    fun clone(lastActionMark: Any?): State
}