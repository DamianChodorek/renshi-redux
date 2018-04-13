package com.damianchodorek.renshi.utils.integrationImpl

import com.damianchodorek.renshi.controller.base.BaseController

/**
 * @author Damian Chodorek
 */
internal class ControllerTestImpl : BaseController<ActivityPluginImpl, StateTestImpl>() {

    var attached = false
    var destroyed = false
    var onAttachOperation: (() -> Unit)? = null

    fun getStoreRef() = store

    fun getPluginRef() = plugin

    override fun onAttachPlugin() {
        attached = true
        onAttachOperation?.invoke()
    }

    override fun onDetachPlugin() {
        attached = false
        super.onDetachPlugin()
    }

    override fun onDestroy() {
        destroyed = true
        super.onDestroy()
    }
}