package com.damianchodorek.renshi.plugin.activity.base.integration

import com.damianchodorek.renshi.controller.CompositeController
import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.store.storeownercache.base.BaseStoreOwnerCache
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshi.utils.RxTestRule
import com.damianchodorek.renshi.utils.integrationImpl.ActivityPluginImpl
import com.damianchodorek.renshi.utils.integrationImpl.ControllerTestImpl
import com.damianchodorek.renshi.utils.integrationImpl.StoreTestImpl
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.pascalwelsch.compositeandroid.activity.ActivityDelegate
import org.junit.Rule

internal abstract class BaseActivityPluginBaseIntegrationTest {

    @Suppress("unused")
    @get:Rule
    val rxRule = RxTestRule()

    protected val controller = ControllerTestImpl()
    protected val compositeController = CompositeController(listOf(
            controller
    ))
    protected val store = StoreTestImpl()
    protected val activityMock = mock<BaseActivity>().apply {
        whenever(this.store).thenReturn(this@BaseActivityPluginBaseIntegrationTest.store)
    }
    protected val cache = BaseStoreOwnerCache()
    protected val controllerProvider = mock<() -> Controller?>().apply {
        whenever(invoke()).thenReturn(compositeController)
    }
    protected val storeChanges = store.stateChanges.test()!!
    protected val plugin = ActivityPluginImpl(
            activity = activityMock,
            storeOwnerCacheProvider = { cache },
            controllerProvider = controllerProvider
    )
    protected val delegate = ActivityDelegate(activityMock)

    protected fun ControllerTestImpl.prepareToModifyPluginOnStoreChanges() = this.apply {
        onAttachOperation = {
            getStoreRef().stateChanges.subscribe {
                getPluginRef()!!.testString = it.testProperty.toString()
            }
        }
    }
}