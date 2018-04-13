package com.damianchodorek.renshi.action

/**
 * Actions are payloads of information that are used to send data from controllers to store.
 * You send them using [com.damianchodorek.renshi.store.Store.dispatch] method.
 * Read more at: https://redux.js.org/basics/actions
 * @author Damian Chodorek
 */
interface Action {

    /**
     * Optional helper field that is stored in [com.damianchodorek.renshi.store.state.State.lastActionMark]
     * after dispatching action. You can use it to identify last dispatched action.
     */
    val actionMark: Any?

    /**
     * Flag indicates that [com.damianchodorek.renshi.store.state.State.lastActionMark] should be
     * set to null immediatelly after action with [actionMark] is dispatched and reduced.
     */
    val singleTime: Boolean
}