package com.damianchodorek.renshi.store.storeownercache

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.store.Store

@Suppress("RedundantUnitReturnType")
interface StoreOwnerCache {

    fun addController(key: String, controller: Controller): Unit
    fun getController(key: String): Controller?
    fun addStore(key: String, store: Store<*>): Unit
    fun getStore(key: String): Store<*>?
    fun onDestroy()
}