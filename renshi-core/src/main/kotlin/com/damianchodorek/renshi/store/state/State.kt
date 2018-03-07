package com.damianchodorek.renshi.store.state

interface State {

    val lastActionRenderMark: Any?
    fun clone(lastActionRenderMark: Any?): State
}