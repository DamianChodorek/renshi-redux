package com.damianchodorek.sample_custom_plugins.controller

import android.util.Log
import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.sample_custom_plugins.plugin.PluginWithLoggingController
import com.damianchodorek.sample_custom_plugins.store.state.ExampleIntentServiceState

/**
 * Logs calls to crucial methods.
 * @param log logging abstraction.
 * @author Damian Chodorek
 */
class LoggingController(
        private val log: (String) -> Unit = { message -> Log.d("LoggingController", message) }
) : BaseController<PluginWithLoggingController, ExampleIntentServiceState>() {

    override fun onAttachPlugin() {
        log("onAttachPlugin")
    }

    override fun onDetachPlugin() {
        log("onDetachPlugin")
        super.onDetachPlugin()
    }

    override fun onDestroy() {
        log("onDestroy")
        super.onDestroy()
    }
}