package com.damianchodorek.renshi.plugin.base

import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * @author Damian Chodorek
 */
class PluginContainerTest {

    private val container = object : PluginContainer<Any>() {
        fun getAllPlugins() = plugins
    }

    @Test
    fun add_addsPlugins() {
        val plugins = listOf<Any>(mock(), mock(), mock())

        plugins.forEach { container.plug(it) }

        assertThat(container.getAllPlugins(), CoreMatchers.equalTo(plugins))
    }
}