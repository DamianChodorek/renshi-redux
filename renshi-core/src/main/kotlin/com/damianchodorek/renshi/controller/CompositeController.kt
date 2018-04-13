package com.damianchodorek.renshi.controller

import com.damianchodorek.renshi.store.Store
import io.reactivex.disposables.Disposable

/**
 * Enables to use group of independent controllers as one controller. You should use this class to
 * attach many controllers to ControllerOwner (see: [com.damianchodorek.renshi.controller.ControllerOwner.createController]).
 * @property controllers list of controllers to compose.
 * @author Damian Chodorek
 */
@Suppress("RedundantUnitReturnType")
open class CompositeController(
        @Suppress("MemberVisibilityCanBePrivate")
        val controllers: List<Controller>
) : Controller {

    /**
     * Throws NotImplementedError. Don't use this method directly.
     */
    override fun disposeOnDetach(disposableToAdd: Disposable): Unit =
            throw NotImplementedError("This method should not be used in this class")

    /**
     * Throws NotImplementedError. Don't use this method directly.
     */
    override fun disposeOnDestroy(disposableToAdd: Disposable): Unit =
            throw NotImplementedError("This method should not be used in this class")

    /**
     * Calls [Controller.onAttachPlugin] on every composed controller.
     */
    override fun onAttachPlugin(): Unit = controllers.forEach { it.onAttachPlugin() }

    /**
     * Calls [Controller.onDetachPlugin] on every composed controller.
     */
    override fun onDetachPlugin(): Unit = controllers.forEach { it.onDetachPlugin() }

    /**
     * Calls [Controller.onDestroy] on every composed controller.
     */
    override fun onDestroy(): Unit = controllers.forEach { it.onDestroy() }

    /**
     * Calls [Controller.setStoreRef] on every composed controller.
     */
    override fun setStoreRef(store: Store<*>): Unit = controllers.forEach { it.setStoreRef(store) }

    /**
     * Calls [Controller.setPluginRef] on every composed controller.
     */
    override fun setPluginRef(plugin: Any): Unit = controllers.forEach { it.setPluginRef(plugin) }
}