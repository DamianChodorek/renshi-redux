package com.damianchodorek.renshi.plugin.activity.base

import android.content.Context
import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.renshi.store.base.BaseStore
import com.damianchodorek.renshi.store.reducer.CompositeReducer
import com.damianchodorek.renshi.store.state.State
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache
import com.damianchodorek.renshi.store.storeownercache.base.BaseStoreOwnerCache
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshi.utils.RxTestRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.pascalwelsch.compositeandroid.activity.ActivityDelegate
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

internal class BaseActivityPluginIntegrationTest {

    @Suppress("unused")
    @get:Rule
    val rxRule = RxTestRule()

    private val testStore = TestStore()
    private val activityMock = mock<BaseActivity>().apply {
        whenever(store).thenReturn(testStore)
    }
    private val delegate = ActivityDelegate(activityMock)
    private val cache = BaseStoreOwnerCache()
    private val plugin = TestActivityPlugin(
            activity = activityMock,
            storeOwnerCacheProvider = { cache }
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        delegate.addPlugin(plugin)
    }

    @Test
    fun onStart_doesNotPutNullToCache_whenNoPresenter() {
        val pluginWithoutPresenter = TestActivityPluginWithoutPresenter(activityMock, { cache })
        delegate.addPlugin(pluginWithoutPresenter)

        delegate.onStart()

        assertThat(cache.getController(pluginWithoutPresenter.getClassName()), nullValue())
    }

}

private data class TestState(
        override val lastActionMark: Any? = null
) : State {

    override fun clone(lastActionRenderMark: Any?) =
            copy(lastActionMark = lastActionRenderMark)
}

private class TestStore : BaseStore<TestState>() {
    override val initialState = TestState()
    override val stateReducer = CompositeReducer<TestState>()
}

private class TestActivityPlugin(
        activity: BaseActivity,
        storeOwnerCacheProvider: (Context) -> StoreOwnerCache
) : BaseActivityPlugin(
        activity = activity,
        storeOwnerCacheProvider = storeOwnerCacheProvider
) {

    override fun createController() = TestController()

    fun getClassName(): String = this::class.java.name
}

private class TestActivityPluginWithoutPresenter(
        activity: BaseActivity,
        private val storeOwnerCacheProvider: (Context) -> StoreOwnerCache
) : BaseActivityPlugin(
        activity = activity,
        storeOwnerCacheProvider = storeOwnerCacheProvider
) {

    override fun createController() = null

    fun getClassName(): String = this::class.java.name
}

private class TestController : BaseController<TestActivityPlugin, TestState>() {

    override fun onAttachPlugin() {

    }
}