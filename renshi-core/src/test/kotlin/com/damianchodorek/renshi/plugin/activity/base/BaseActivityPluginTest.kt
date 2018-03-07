package com.damianchodorek.renshi.plugin.activity.base

import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshi.utils.BasePluginTest
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pascalwelsch.compositeandroid.activity.ActivityDelegate
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.InjectMocks

internal class BaseActivityPluginTest : BasePluginTest() {

    private val activityMock = mock<BaseActivity>()
    private val delegate = ActivityDelegate(activityMock)

    @InjectMocks
    private var plugin = object : BaseActivityPlugin(
            activityMock,
            pluginDelegateBuilderMock
    ) {
        override fun createController() = controllerMock
    }

    override fun addPlugin() {
        delegate.addPlugin(plugin)
    }

    @Test
    fun onStart_createsDelegateUsingProperStoreProvider() {
        val storeMock = mock<Store<*>>()
        whenever(activityMock.store).thenReturn(storeMock)

        delegate.onStart()

        assertThat(builderCaptor.storeProvider.firstValue(), equalTo(storeMock))
    }

    @Test
    fun onStart_createsDelegateUsingProperPluginCache() {
        val cacheMock = mock<StoreOwnerCache>()
        whenever(viewModelCacheProviderMock.provide(activityMock)).thenReturn(cacheMock)

        delegate.onStart()

        assertThat(builderCaptor.cache.firstValue(), equalTo(cacheMock))
    }

    @Test
    fun onStart_createsDelegateUsingProperControllerProvider() {
        delegate.onStart()
        assertThat(builderCaptor.controllerProvider.firstValue(), equalTo(controllerMock))
    }

    @Test
    fun onStart_createsDelegateUsingProperPluginProvider() {
        delegate.onStart()
        assertThat(builderCaptor.pluginProvider.firstValue(), equalTo(plugin as Any))
    }

    @Test
    fun onStart_createsDelegateUsingProperPluginNameProvider() {
        delegate.onStart()
        assertThat(builderCaptor.pluginNameProvider.firstValue(), equalTo(plugin::class.java.name))
    }

    @Test
    fun onStart_invokesOnStartPluginOnDelegate() {
        delegate.onStart()
        verify(pluginDelegaterMock).onStartPlugin()
    }

    @Test
    fun onStop_invokesOnStopPluginOnDelegate() {
        delegate.onStop()
        verify(pluginDelegaterMock).onStopPlugin()
    }
}