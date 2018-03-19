package com.damianchodorek.renshi.store.state

interface State {

    val lastActionMark: Any?
    fun clone(lastActionMark: Any?): State
}