package com.damianchodorek.renshi.controller

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.state.State
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class CompositeControllerTest {

    private val controllerMocks = listOf<BaseController<BaseActivity, State>>(mock(), mock())

    @Test
    fun onAttachPlugin_callsOnAttachPluginOnControllers() {
        CompositeController(controllerMocks).onAttachPlugin()
        controllerMocks.forEach { verify(it).onAttachPlugin() }
    }

    @Test
    fun onDetachPlugin_callsOnDetachPluginOnControllers() {
        CompositeController(controllerMocks).onDetachPlugin()
        controllerMocks.forEach { verify(it).onDetachPlugin() }
    }

    @Test
    fun onDestroy_callsOnDestroyOnControllers() {
        CompositeController(controllerMocks).onDestroy()
        controllerMocks.forEach { verify(it).onDestroy() }
    }

    @Test
    fun setStore_callsSetStoreOnControllers() {
        val storeMock = mock<Store<*>>()

        CompositeController(controllerMocks).setStoreRef(storeMock)

        controllerMocks.forEach { verify(it).setStoreRef(storeMock) }
    }

    @Test
    fun setPluginRef_callsSetPluginRefOnControllers() {
        val pluginMock = mock<BaseActivity>()

        CompositeController(controllerMocks).setPluginRef(pluginMock)

        controllerMocks.forEach { verify(it).setPluginRef(pluginMock) }
    }
}