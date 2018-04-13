package com.damianchodorek.renshi.store.storeownercache.base

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache

/**
 * Base implementation of [StoreOwnerCache] that uses LinkedHashMap as cache.
 * @author Damian Chodorek
 */
class BaseStoreOwnerCache : StoreOwnerCache {

    internal val controllers: MutableMap<String, Controller> = mutableMapOf()
    internal val stores: MutableMap<String, Store<*>> = mutableMapOf()

    override fun addController(key: String, controller: Controller) {
        controllers[key] = controller
    }

    override fun getController(key: String): Controller? = controllers[key]

    override fun addStore(key: String, store: Store<*>) {
        stores[key] = store
    }

    override fun getStore(key: String): Store<*>? = stores[key]

    override fun onDestroy() {
        controllers.values.forEach { it.onDestroy() }
        controllers.clear()
        stores.clear()
    }
}