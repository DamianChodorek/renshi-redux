package com.damianchodorek.renshi.plugin.dialogfragment.base

import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshi.storeowner.BaseDialogFragment
import com.damianchodorek.renshi.utils.BasePluginTest
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pascalwelsch.compositeandroid.fragment.FragmentDelegate
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test
import org.mockito.InjectMocks

class BaseDialogFragmentPluginTest : BasePluginTest() {

    private val activitytMock = mock<BaseActivity>()
    private val fragmentMock = mock<BaseDialogFragment>().apply {
        whenever(activity).thenReturn(activitytMock)
    }
    private val delegate = FragmentDelegate(fragmentMock)

    @InjectMocks
    private var plugin = object : BaseDialogFragmentPlugin(
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

        MatcherAssert.assertThat(builderCaptor.storeProvider.firstValue(), CoreMatchers.equalTo(storeMock))
    }

    @Test
    fun onStart_createsDelegateUsingProperPluginCache() {
        val cacheMock = mock<StoreOwnerCache>()
        whenever(viewModelCacheProviderMock.provide(activitytMock)).thenReturn(cacheMock)

        delegate.onStart()

        MatcherAssert.assertThat(builderCaptor.cache.firstValue(), CoreMatchers.equalTo(cacheMock))
    }

    @Test
    fun onStart_createsDelegateUsingProperControllerProvider() {
        delegate.onStart()
        MatcherAssert.assertThat(builderCaptor.controllerProvider.firstValue(), CoreMatchers.equalTo(controllerMock))
    }

    @Test
    fun onStart_createsDelegateUsingProperPluginProvider() {
        delegate.onStart()
        MatcherAssert.assertThat(builderCaptor.pluginProvider.firstValue(), CoreMatchers.equalTo(plugin as Any))
    }

    @Test
    fun onStart_createsDelegateUsingProperPluginNameProvider() {
        delegate.onStart()
        MatcherAssert.assertThat(builderCaptor.pluginNameProvider.firstValue(), CoreMatchers.equalTo(plugin::class.java.name))
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