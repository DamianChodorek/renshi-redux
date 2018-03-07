package com.damianchodorek.renshi.plugin.fragment.base

import com.damianchodorek.renshi.controller.ControllerOwner
import com.damianchodorek.renshi.plugin.PluginDelegate
import com.damianchodorek.renshi.plugin.base.PluginDelegateBuilder
import com.damianchodorek.renshi.store.storeownercache.base.ViewModelCacheProvider
import com.damianchodorek.renshi.storeowner.BaseFragment

@Suppress("RedundantUnitReturnType")
abstract class BaseFragmentPlugin(
        fragment: BaseFragment,
        private var pluginDelegateBuilder: PluginDelegateBuilder = PluginDelegateBuilder()
) : SimpleFragmentPlugin(fragment), ControllerOwner {

    private var storeOwnerCacheProvider = ViewModelCacheProvider()
    private val pluginDelegate: PluginDelegate by lazy {
        pluginDelegateBuilder.build(
                storeProvider = { fragment.store },
                storeOwnerCacheProvider = { storeOwnerCacheProvider.provide(fragment.activity!!) },
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
