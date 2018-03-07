package com.damianchodorek.renshi.plugin

@Suppress("RedundantUnitReturnType")
interface PluginDelegate {

    fun onStartPlugin(): Unit
    fun onStopPlugin(): Unit
}