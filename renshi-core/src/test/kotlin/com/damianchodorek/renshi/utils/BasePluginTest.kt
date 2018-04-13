package com.damianchodorek.renshi.utils

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.plugin.PluginDelegate
import com.damianchodorek.renshi.plugin.base.PluginDelegateBuilder
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache
import com.damianchodorek.renshi.store.storeownercache.base.ViewModelCacheProvider
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.mockito.MockitoAnnotations

/**
 * @author Damian Chodorek
 */
abstract class BasePluginTest {

    protected val builderCaptor = PluginDelegateBuilderCaptors()
    protected val viewModelCacheProviderMock = mock< ViewModelCacheProvider>()
    protected val pluginDelegateBuilderMock = mock<PluginDelegateBuilder>()
    protected val pluginDelegaterMock = mock<PluginDelegate>()
    protected val controllerMock = mock<Controller>()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        addPlugin()
        prepareDelegateBuilder()
    }

    protected abstract fun addPlugin()

    private fun prepareDelegateBuilder(pluginDelegte: PluginDelegate = pluginDelegaterMock) {
        whenever(pluginDelegateBuilderMock.build(
                builderCaptor.storeProvider.capture(),
                builderCaptor.cache.capture(),
                builderCaptor.controllerProvider.capture(),
                builderCaptor.pluginProvider.capture(),
                builderCaptor.pluginNameProvider.capture()
        )).thenReturn(pluginDelegte)
    }
}

class PluginDelegateBuilderCaptors {

    val storeProvider = argumentCaptor<() -> Store<*>>()
    val cache = argumentCaptor<() -> StoreOwnerCache>()
    val controllerProvider = argumentCaptor<() -> Controller?>()
    val pluginProvider = argumentCaptor<() -> Any>()
    val pluginNameProvider = argumentCaptor<() -> String>()
}