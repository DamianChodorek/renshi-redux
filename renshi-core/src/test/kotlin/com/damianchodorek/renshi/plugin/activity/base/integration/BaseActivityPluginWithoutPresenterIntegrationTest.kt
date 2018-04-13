package com.damianchodorek.renshi.plugin.activity.base.integration

import android.content.Context
import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.plugin.activity.base.BaseActivityPlugin
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshi.utils.integrationImpl.StoreTestImpl
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * @author Damian Chodorek
 */
internal class BaseActivityPluginWithoutPresenterIntegrationTest : BaseActivityPluginBaseIntegrationTest() {

    @Test
    fun onStart_doesNotPutAnythingToCache_whenNoControllerCreated() {
        val pluginWithoutController = TestActivityPluginWithoutController(activityMock, { cache })
        delegate.addPlugin(pluginWithoutController)

        delegate.onStart()

        val expectedController = cache.getController(pluginWithoutController.getClassName())
        assertThat(expectedController, nullValue())
    }

    @Test
    fun onStart_putsStoreToCache_whenNoControllerCreated() {
        val pluginWithoutController = TestActivityPluginWithoutController(activityMock, { cache })
        delegate.addPlugin(pluginWithoutController)

        delegate.onStart()

        val expectedStore = cache.getStore(store.getClassName()) as StoreTestImpl
        assertThat(expectedStore, equalTo(store))
    }
}

private class TestActivityPluginWithoutController(
        activity: BaseActivity,
        storeOwnerCacheProvider: (Context) -> StoreOwnerCache
) : BaseActivityPlugin(
        activity = activity,
        storeOwnerCacheProvider = storeOwnerCacheProvider
) {

    override fun createController(): Controller? = null

    fun getClassName(): String = this::class.java.name
}