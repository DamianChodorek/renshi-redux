package com.damianchodorek.renshi.action.base

import com.damianchodorek.renshi.action.Action

/**
 * Default implementation of [Action]. Your actions should inherit from this class  to reduce boilerplate.
 * @author Damian Chodorek
 */
abstract class BaseAction : Action {

    override val actionMark: Any? = null
    override val singleTime: Boolean = false
}