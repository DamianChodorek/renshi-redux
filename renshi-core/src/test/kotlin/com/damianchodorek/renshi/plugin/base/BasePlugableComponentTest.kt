package com.damianchodorek.renshi.plugin.base

import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

class BasePlugableComponentTest {

    private val component = object : BasePlugableComponent<Any>() {
        fun getAllPlugins() = plugins
    }

    @Test
    fun add_addsPlugins() {
        val plugins = listOf<Any>(mock(), mock(), mock())

        plugins.forEach { component.addPlugin(it) }

        MatcherAssert.assertThat(component.getAllPlugins(), CoreMatchers.equalTo(plugins))
    }
}