package com.damianchodorek.renshi.action.base

import com.damianchodorek.renshi.action.Action

abstract class BaseAction : Action {

    override val renderMark: Any? = null
    override val singleTime: Boolean = false
}