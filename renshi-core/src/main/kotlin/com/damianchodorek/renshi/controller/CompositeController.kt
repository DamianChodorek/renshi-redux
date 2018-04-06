package com.damianchodorek.renshi.controller

import com.damianchodorek.renshi.store.Store
import io.reactivex.disposables.Disposable

@Suppress("RedundantUnitReturnType")
open class CompositeController(
        @Suppress("MemberVisibilityCanBePrivate")
        val controllers: List<Controller>
) : Controller {

    override fun disposeOnDetach(disposableToAdd: Disposable): Unit =
            throw NotImplementedError("This method should not be used in this class")

    override fun disposeOnDestroy(disposableToAdd: Disposable): Unit =
            throw NotImplementedError("This method should not be used in this class")

    override fun onAttachPlugin(): Unit = controllers.forEach { it.onAttachPlugin() }

    override fun onDetachPlugin(): Unit = controllers.forEach { it.onDetachPlugin() }

    override fun onDestroy(): Unit = controllers.forEach { it.onDestroy() }

    override fun setStoreRef(store: Store<*>): Unit = controllers.forEach { it.setStoreRef(store) }

    override fun setPluginRef(plugin: Any): Unit = controllers.forEach { it.setPluginRef(plugin) }
}