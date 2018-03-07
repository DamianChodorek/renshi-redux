package com.damianchodorek.renshi.plugin.fragment.base

import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshi.storeowner.BaseFragment
import com.damianchodorek.renshi.utils.BasePluginTest
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pascalwelsch.compositeandroid.fragment.FragmentDelegate
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.InjectMocks

class BaseFragmentPluginTest : BasePluginTest() {

    private val activitytMock = mock<BaseActivity>()
    private val fragmentMock = mock<BaseFragment>().apply {
        whenever(activity).thenReturn(activitytMock)
    }
    private val delegate = FragmentDelegate(fragmentMock)

    @InjectMocks
    private var plugin = object : BaseFragmentPlugin(
            fragmentMock,
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
        whenever(fragmentMock.store).thenReturn(storeMock)

        delegate.onStart()

        assertThat(builderCaptor.storeProvider.firstValue(), equalTo(storeMock))
    }

    @Test
    fun onStart_createsDelegateUsingProperPluginCache() {
        val cacheMock = mock<StoreOwnerCache>()
        whenever(viewModelCacheProviderMock.provide(activitytMock)).thenReturn(cacheMock)

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