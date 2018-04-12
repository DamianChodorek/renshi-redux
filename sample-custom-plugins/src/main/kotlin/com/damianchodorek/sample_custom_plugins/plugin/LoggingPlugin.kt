package com.damianchodorek.sample_custom_plugins.plugin

import android.content.Intent
import android.util.Log
import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.sample_custom_plugins.ExampleIntentService
import com.damianchodorek.sample_custom_plugins.plugin.base.BaseIntentServicePlugin

/**
 * Logs service methods when called.
 * @param service intent service that delegates to this plugin.
 * @param log logging abstraction.
 */
class LoggingPlugin(
        service: ExampleIntentService,
        private val log: (String) -> Unit = { message -> Log.d("LoggingPlugin", message) }
) : BaseIntentServicePlugin(service) {

    override fun onCreate() = log("onCreate")

    override fun onDestroy() = log("onDestroy")

    override fun onHandleIntent(intent: Intent?) = log("onHandleIntent")

    // controllers are optional, so not every plugin must create them
    override fun createController(): Controller? = null
}