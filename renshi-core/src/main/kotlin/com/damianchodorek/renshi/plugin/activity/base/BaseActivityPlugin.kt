package com.damianchodorek.renshi.plugin.activity.base

import com.damianchodorek.renshi.controller.ControllerOwner
import com.damianchodorek.renshi.plugin.PluginDelegate
import com.damianchodorek.renshi.plugin.base.PluginDelegateBuilder
import com.damianchodorek.renshi.store.storeownercache.base.ViewModelCacheProvider
import com.damianchodorek.renshi.storeowner.BaseActivity

@Suppress("RedundantUnitReturnType")
abstract class BaseActivityPlugin(
        activity: BaseActivity,
        private var pluginDelegateBuilder: PluginDelegateBuilder = PluginDelegateBuilder()
) : SimpleActivityPlugin(activity), ControllerOwner {

    private var storeOwnerCacheProvider = ViewModelCacheProvider()
    private val pluginDelegate: PluginDelegate by lazy {
        pluginDelegateBuilder.build(
                storeProvider = { activity.store },
                storeOwnerCacheProvider = { storeOwnerCacheProvider.provide(activity) },
                controllerProvider = { createController() },
                pluginProvider = { this }
        )
    }

    override fun onStart(): Unit {
        super.onStart()
        pluginDelegate.onStartPlugin()
    }

    override fun onStop(): Unit {
        pluginDelegate.onStopPlugin()
        super.onStop()
    }
}