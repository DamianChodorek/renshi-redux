package com.damianchodorek.renshi.controller

import com.damianchodorek.renshi.store.Store
import io.reactivex.disposables.Disposable

/**
 * Handles events from its plugin and dispatches actions ([com.damianchodorek.renshi.action.Action])
 * to store ([com.damianchodorek.renshi.store.Store]). [Controller] should contain pure logic.
 * It should be testable without Robolectric or Espresso. If needed - data requests should be delegated
 * to special Data Access Objects. [Controller] should not depend directly on android or other frameworks.
 * @author Damian Chodorek
 */
@Suppress("RedundantUnitReturnType")
interface Controller {

    /**
     * Called when plugin is attached to controller.
     */
    fun onAttachPlugin(): Unit

    /**
     * Called when plugin is detached from controller.
     */
    fun onDetachPlugin(): Unit

    /**
     * Called before store is destroyed.
     */
    fun onDestroy(): Unit

    /**
     * Sets current store instance. Method is called once, usually before firs [setPluginRef] is called.
     * @param store instance.
     */
    fun setStoreRef(store: Store<*>): Unit

    /**
     * Sets current plugin instance. May be called many times with different plugin jus before
     * [onAttachPlugin].
     * @param plugin plugin instance.
     */
    fun setPluginRef(plugin: Any): Unit

    /**
     * Disposes [disposableToAdd] when [onDetachPlugin] is called.
     * @param disposableToAdd disposable instance.
     */
    fun disposeOnDetach(disposableToAdd: Disposable): Unit

    /**
     * Disposes [disposableToAdd] when [onDestroy] is called.
     * @param disposableToAdd disposable instance.
     */
    fun disposeOnDestroy(disposableToAdd: Disposable): Unit
}