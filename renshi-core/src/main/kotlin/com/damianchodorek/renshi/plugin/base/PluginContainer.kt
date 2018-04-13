package com.damianchodorek.renshi.plugin.base

import com.damianchodorek.renshi.plugin.Plugable

/**
 * Base implementation of [Plugable] to reduce boilerplate.
 * @param PLUGIN the type of plugin.
 * @author Damian Chodorek
 */
@Suppress("RedundantUnitReturnType")
open class PluginContainer<PLUGIN> : Plugable<PLUGIN> {

    protected val plugins = mutableListOf<PLUGIN>()

    override fun plug(plugin: PLUGIN): Unit {
        plugins.add(plugin)
    }
}