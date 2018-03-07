package com.damianchodorek.renshi.controller

import com.damianchodorek.renshi.store.Store

internal class ControllerInitializer {

    fun init(
            store: Store<*>,
            createController: () -> Controller?,
            getControllerFromViewCache: () -> Controller?,
            saveControllerToViewCache: (controller: Controller) -> Unit,
            saveStoreToStoreOwnerCache: (store: Store<*>) -> Unit
    ): Controller? {
        saveStoreToStoreOwnerCache(store)

        var cachedController = getControllerFromViewCache()

        if (cachedController == null) {
            cachedController = createController()
            cachedController?.run {
                this.setStoreRef(store)
                saveControllerToViewCache(this)
            }
        }

        return cachedController
    }
}