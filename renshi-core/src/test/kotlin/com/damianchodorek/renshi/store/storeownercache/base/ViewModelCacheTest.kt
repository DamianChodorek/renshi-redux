package com.damianchodorek.renshi.store.storeownercache.base

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.renshi.plugin.activity.base.SimpleActivityPlugin
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.state.State
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test

class ViewModelCacheTest {

    class Plugin1 : SimpleActivityPlugin(mock())
    class Plugin2 : SimpleActivityPlugin(mock())
    class Plugin3 : SimpleActivityPlugin(mock())
    class Plugin4 : SimpleActivityPlugin(mock())

    private val pluginClasses = listOf(
            Plugin1::class.java.name,
            Plugin2::class.java.name,
            Plugin3::class.java.name,
            Plugin4::class.java.name
    )
    private val cache = ViewModelCache()
    private val controllers = mutableListOf<Pair<String,
            BaseController<BaseActivity, State>>>().apply {
        pluginClasses.forEach { add(Pair(it, mock())) }
    }
    private val stores = mutableListOf<Pair<String, Store<*>>>().apply {
        pluginClasses.forEach { add(Pair(it, mock())) }
    }

    @Before
    fun setUp() {
        controllers.forEach { cache.addController(it.first, it.second) }
        stores.forEach { cache.addStore(it.first, it.second) }
    }

    @Test
    fun onCleared_clearsControllers() {
        cache.onClearedInternal()
        MatcherAssert.assertThat(cache.baseStoreOwnerCache.controllers.size, Matchers.equalTo(0))
    }

    @Test
    fun onCleared_clearsStores() {
        cache.onClearedInternal()
        MatcherAssert.assertThat(cache.baseStoreOwnerCache.stores.size, Matchers.equalTo(0))
    }

    @Test
    fun onCleared_callsOnDestroyOnAllControllers() {
        cache.onClearedInternal()
        controllers.forEach { verify(it.second).onDestroy() }
    }
}