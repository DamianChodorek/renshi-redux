package com.damianchodorek.renshi.plugin.base

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.plugin.PluginDelegate
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache

class PluginDelegateBuilder {

    fun build(
            storeProvider: () -> Store<*>,
            storeOwnerCacheProvider: () -> StoreOwnerCache,
            controllerProvider: () -> Controller?,
            pluginProvider: () -> Any,
            pluginNameProvider: () -> String = { pluginProvider()::class.java.name }
    ): PluginDelegate =
            PluginDelegateImpl(
                    storeProvider = storeProvider,
                    storeOwnerCacheProvider = storeOwnerCacheProvider,
                    controllerProvider = controllerProvider,
                    pluginProvider = pluginProvider,
                    pluginNameProvider = pluginNameProvider
            )
}