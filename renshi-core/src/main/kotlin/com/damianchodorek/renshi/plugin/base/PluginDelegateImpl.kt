package com.damianchodorek.renshi.plugin.base

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.controller.ControllerInitializer
import com.damianchodorek.renshi.plugin.PluginDelegate
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache

@Suppress("RedundantUnitReturnType")
class PluginDelegateImpl(
        private val storeProvider: () -> Store<*>,
        private val storeOwnerCacheProvider: () -> StoreOwnerCache,
        private val controllerProvider: () -> Controller?,
        private val pluginProvider: () -> Any,
        private val pluginNameProvider: () -> String = { pluginProvider()::class.java.name }
) : PluginDelegate {

    private var started = false
    private var controllerInitializer = ControllerInitializer()

    override fun onStartPlugin(): Unit {
        if (started) return
        started = true

        controllerInitializer.init(
                store = storeProvider(),
                createController = controllerProvider,
                getControllerFromStoreOwnerCache = { getControllerFromStoreOwnerCache() },
                saveControllerToStoreOwnerCache = { controller -> saveControllerToStoreOwnerCache(controller) },
                saveStoreToStoreOwnerCache = { store -> saveStoreToStoreOwnerCache(store) }
        )?.apply {
            setPluginRef(pluginProvider())
            onAttachPlugin()
        }
    }

    override fun onStopPlugin(): Unit {
        if (started.not()) return
        started = false
        getControllerFromStoreOwnerCache()?.onDetachPlugin()
    }

    private fun getControllerFromStoreOwnerCache(): Controller? =
            storeOwnerCacheProvider().getController(pluginNameProvider())

    private fun saveControllerToStoreOwnerCache(controller: Controller) {
        storeOwnerCacheProvider().addController(pluginNameProvider(), controller)
    }

    private fun saveStoreToStoreOwnerCache(store: Store<*>) {
        storeOwnerCacheProvider().addStore(store::class.java.name, store)
    }
}