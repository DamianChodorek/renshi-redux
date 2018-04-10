package com.damianchodorek.renshi.plugin

/**
 * Interface for all classes that are extended by plugins.
 * @param PLUGIN the type of plugin.
 */
@Suppress("RedundantUnitReturnType")
interface Plugable<in PLUGIN> {

    /**
     * Adds plugin to plugable class.
     * @param plugin plugin to add.
     */
    fun addPlugin(plugin: PLUGIN): Unit
}