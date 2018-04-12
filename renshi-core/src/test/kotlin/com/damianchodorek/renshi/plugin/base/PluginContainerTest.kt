package com.damianchodorek.renshi.plugin.base

import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

class PluginContainerTest {

    private val container = object : PluginContainer<Any>() {
        fun getAllPlugins() = plugins
    }

    @Test
    fun add_addsPlugins() {
        val plugins = listOf<Any>(mock(), mock(), mock())

        plugins.forEach { container.plug(it) }

        MatcherAssert.assertThat(container.getAllPlugins(), CoreMatchers.equalTo(plugins))
    }
}