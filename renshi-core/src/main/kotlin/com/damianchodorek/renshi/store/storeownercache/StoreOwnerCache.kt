package com.damianchodorek.renshi.store.storeownercache

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.store.Store

/**
 * Cache that every [com.damianchodorek.renshi.store.StoreOwner] should have to save attached
 * controllers ([com.damianchodorek.renshi.controller.Controller]) and its store [com.damianchodorek.renshi.store.Store].
 * Cache should be cleared before store owner is destroyed and won't be recreated. For example
 * when activity orientation changes, cache shouldn't be cleared.
 */
@Suppress("RedundantUnitReturnType")
interface StoreOwnerCache {

    /**
     * Adds controller to cache.
     * @param key key to identify controller.
     * @param controller controller to add.
     */
    fun addController(key: String, controller: Controller): Unit

    /**
     * Returns controller from cache.
     * @param key key to identify controller.
     * @return controller from cache or null.
     */

    fun getController(key: String): Controller?

    /**
     * Adds store to cache.
     * @param key key to identify store.
     * @param store store to add.
     */
    fun addStore(key: String, store: Store<*>): Unit

    /**
     * Returns store from cache.
     * @param key key to identify store.
     * @return store from cache or null.
     */
    fun getStore(key: String): Store<*>?

    /**
     * Clears cache and calls [com.damianchodorek.renshi.controller.Controller.onDestroy].
     */
    fun onDestroy()
}