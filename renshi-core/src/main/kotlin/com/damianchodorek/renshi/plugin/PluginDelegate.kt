package com.damianchodorek.renshi.plugin

/**
 * Base interface for plugin delegates.
 */
@Suppress("RedundantUnitReturnType")
interface PluginDelegate {

    /**
     * Called when plugin should be started. For example in Activity.onStart.
     */
    fun onStartPlugin(): Unit

    /**
     * Called when plugin should be stopped. For example in Activity.onStop.
     */
    fun onStopPlugin(): Unit
}