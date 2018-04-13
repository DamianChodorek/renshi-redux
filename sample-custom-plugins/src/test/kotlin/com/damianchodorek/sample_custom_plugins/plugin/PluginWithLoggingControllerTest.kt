package com.damianchodorek.sample_custom_plugins.plugin

import com.damianchodorek.sample_custom_plugins.controller.LoggingController
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * @author Damian Chodorek
 */
class PluginWithLoggingControllerTest {

    private val plugin = PluginWithLoggingController(mock())

    @Test
    fun createController_createsProperControllerInstance() {
        assertThat(plugin.createController(), instanceOf(LoggingController::class.java))
    }
}