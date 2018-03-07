package com.damianchodorek.renshi.plugin.dialogfragment.base

import com.damianchodorek.renshi.controller.ControllerOwner
import com.damianchodorek.renshi.plugin.PluginDelegate
import com.damianchodorek.renshi.plugin.base.PluginDelegateBuilder
import com.damianchodorek.renshi.store.storeownercache.base.ViewModelCacheProvider
import com.damianchodorek.renshi.storeowner.BaseDialogFragment

@Suppress("RedundantUnitReturnType")
abstract class BaseDialogFragmentPlugin(
        dialogFragment: BaseDialogFragment,
        private var pluginDelegateBuilder: PluginDelegateBuilder = PluginDelegateBuilder()
) : SimpleDialogFragmentPlugin(dialogFragment), ControllerOwner {

    private var storeOwnerCacheProvider = ViewModelCacheProvider()
    private val pluginDelegate: PluginDelegate by lazy {
        pluginDelegateBuilder.build(
                storeProvider = { dialogFragment.store },
                storeOwnerCacheProvider = { storeOwnerCacheProvider.provide(dialogFragment.activity!!) },
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
