package com.damianchodorek.sample_custom_plugins.plugin

import com.damianchodorek.sample_custom_plugins.ExampleIntentService
import com.damianchodorek.sample_custom_plugins.controller.LoggingController
import com.damianchodorek.sample_custom_plugins.plugin.base.BaseIntentServicePlugin

/**
 * Has controller that logs all its lifecycle methods.
 */
class PluginWithLoggingController(
        service: ExampleIntentService
) : BaseIntentServicePlugin(service) {

    override fun createController() = LoggingController()
}