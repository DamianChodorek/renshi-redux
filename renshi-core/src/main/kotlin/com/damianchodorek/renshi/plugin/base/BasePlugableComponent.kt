package com.damianchodorek.renshi.plugin.base

import com.damianchodorek.renshi.plugin.Plugable

@Suppress("MemberVisibilityCanBePrivate", "unused", "RedundantUnitReturnType")
open class BasePlugableComponent<PLUGIN> : Plugable<PLUGIN> {

    protected val plugins = mutableListOf<PLUGIN>()

    override fun addPlugin(plugin: PLUGIN): Unit {
        plugins.add(plugin)
    }
}