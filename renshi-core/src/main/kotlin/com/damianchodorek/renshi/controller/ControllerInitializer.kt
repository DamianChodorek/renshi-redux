package com.damianchodorek.renshi.controller

import com.damianchodorek.renshi.store.Store

/**
 * Helper class used by [com.damianchodorek.renshi.plugin.base.PluginDelegateImpl] to
 * initialize controllers.
 * @author Damian Chodorek
 */
internal class ControllerInitializer {

    /**
     * Creates new controller and saves it to cache or returns previously cached controller.
     * @param store instance of [com.damianchodorek.renshi.store.Store] to set as controller reference.
     * @param createController function that returns new instance of [Controller]
     * @param getControllerFromStoreOwnerCache function that returns controller from [com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache]
     * @param saveControllerToStoreOwnerCache function that saves created controller to [com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache]
     * @param saveStoreToStoreOwnerCache function that saves store to [com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache]
     * @return created or cached controller.
     */
    fun init(
            store: Store<*>,
            createController: () -> Controller?,
            getControllerFromStoreOwnerCache: () -> Controller?,
            saveControllerToStoreOwnerCache: (controller: Controller) -> Unit,
            saveStoreToStoreOwnerCache: (store: Store<*>) -> Unit
    ): Controller? {
        saveStoreToStoreOwnerCache(store)

        var cachedController = getControllerFromStoreOwnerCache()

        if (cachedController == null) {
            cachedController = createController()
            cachedController?.run {
                this.setStoreRef(store)
                saveControllerToStoreOwnerCache(this)
            }
        }

        return cachedController
    }
}