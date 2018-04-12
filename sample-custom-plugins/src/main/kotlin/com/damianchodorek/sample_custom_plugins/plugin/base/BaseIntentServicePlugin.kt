package com.damianchodorek.sample_custom_plugins.plugin.base

import android.content.Intent
import com.damianchodorek.renshi.controller.ControllerOwner
import com.damianchodorek.renshi.plugin.PluginDelegate
import com.damianchodorek.renshi.plugin.base.PluginDelegateBuilder
import com.damianchodorek.sample_custom_plugins.ExampleIntentService

abstract class BaseIntentServicePlugin(
        service: ExampleIntentService
) : IntentServicePlugin, ControllerOwner {

    // we use plugin delegate to handle initializing controllers
    private val pluginDelegate: PluginDelegate by lazy {
        PluginDelegateBuilder().build(
                storeProvider = { service.store },
                storeOwnerCacheProvider = { service.cache },
                controllerProvider = { createController() },
                pluginProvider = { this }
        )
    }

    // we need to start plugin to setup and attach controllers
    override fun onCreate() = pluginDelegate.onStartPlugin()

    // we need to stop plugin to detach controllers
    override fun onDestroy() = pluginDelegate.onStopPlugin()

    override fun onHandleIntent(intent: Intent?) {}
}