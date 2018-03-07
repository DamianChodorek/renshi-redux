package com.damianchodorek.renshi.action

interface Action {

    val renderMark: Any?
    val singleTime: Boolean
}