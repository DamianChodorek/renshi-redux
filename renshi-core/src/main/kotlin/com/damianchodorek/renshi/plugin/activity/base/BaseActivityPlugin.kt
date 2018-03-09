package com.damianchodorek.renshi.plugin.activity.base

import android.content.Context
import com.damianchodorek.renshi.controller.ControllerOwner
import com.damianchodorek.renshi.plugin.PluginDelegate
import com.damianchodorek.renshi.plugin.base.PluginDelegateBuilder
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache
import com.damianchodorek.renshi.store.storeownercache.base.ViewModelCacheProvider
import com.damianchodorek.renshi.storeowner.BaseActivity

@Suppress("RedundantUnitReturnType")
abstract class BaseActivityPlugin(
        activity: BaseActivity,
        private val pluginDelegateBuilder: PluginDelegateBuilder = PluginDelegateBuilder(),
        private val storeOwnerCacheProvider: (Context) -> StoreOwnerCache =
                { context -> ViewModelCacheProvider().provide(context) }
) : SimpleActivityPlugin(activity), ControllerOwner {

    private val pluginDelegate: PluginDelegate by lazy {
        pluginDelegateBuilder.build(
                storeProvider = { activity.store },
                storeOwnerCacheProvider = { storeOwnerCacheProvider(activity) },
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