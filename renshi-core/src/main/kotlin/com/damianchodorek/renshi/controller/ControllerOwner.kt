package com.damianchodorek.renshi.controller

/**
 * Interface for all classes that may have controllers.
 * [com.damianchodorek.renshi.plugin.activity.base.BaseActivityPlugin] implements this interface.
 */
interface ControllerOwner {

    /**
     * Creates new [Controller].
     * @return [Controller] instance or null.
     */
    fun createController(): Controller?
}