package com.damianchodorek.renshi.plugin

@Suppress("RedundantUnitReturnType")
interface Plugable<in PLUGIN> {

    fun addPlugin(plugin: PLUGIN): Unit
}