package com.damianchodorek.renshi.plugin.activity.base.integration

import com.damianchodorek.renshi.utils.integrationImpl.ActivityPluginImpl
import com.damianchodorek.renshi.utils.integrationImpl.ControllerTestImpl
import com.pascalwelsch.compositeandroid.activity.ActivityDelegate
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

internal class BaseActivityPluginMeroryIntegrationTest : BaseActivityPluginBaseIntegrationTest() {

    @Test
    fun onStart_setsPluginsWeakReferenceInController() {
        val controller = ControllerTestImpl()
        startWeakPlugin(controller)

        System.gc()

        assertThat(controller.getPluginRef(), nullValue())
    }

    private fun startWeakPlugin(pluginsController: ControllerTestImpl) =
            ActivityPluginImpl(
                    activity = activityMock,
                    storeOwnerCacheProvider = { cache },
                    controllerProvider = { pluginsController }
            ).also {
                ActivityDelegate(activityMock).apply {
                    addPlugin(it)
                    onStart()
                }
            }.also {
                assertThat(pluginsController.getPluginRef(), equalTo(it))
            }

    @Test
    fun onStart_pluginCanBeGarbageCollected_whenPresenterUsesPluginInSubscription() {
        val controller = ControllerTestImpl()
        controller.prepareToModifyPluginOnStoreChanges()
        startWeakPlugin(controller)

        System.gc()

        assertThat(controller.getPluginRef(), nullValue())
    }
}