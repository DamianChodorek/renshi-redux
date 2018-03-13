package com.damianchodorek.renshi.action.base

import com.damianchodorek.renshi.action.Action

abstract class BaseAction : Action {

    override val actionMark: Any? = null
    override val singleTime: Boolean = false
}