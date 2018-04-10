package com.damianchodorek.renshi.plugin.base

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.plugin.PluginDelegate
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache

/**
 * Builder for [PluginDelegate].
 */
class PluginDelegateBuilder {

    /**
     * Builds new plugin delegate.
     * @param storeProvider function that returns instance of [com.damianchodorek.renshi.store.Store].
     * @param storeOwnerCacheProvider function that returns instance of [com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache].
     * @param controllerProvider function that returns optional instance of [com.damianchodorek.renshi.controller.Controller].
     * @param pluginProvider function that returns plugin.
     * @param pluginNameProvider function that returns name of the plugin. By default it is plugin class name.
     */
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