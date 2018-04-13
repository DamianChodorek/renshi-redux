package com.damianchodorek.renshi.store.storeownercache.base

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.renshi.plugin.activity.base.SimpleActivityPlugin
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.state.State
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test

/**
 * @author Damian Chodorek
 */
class BaseStoreOwnerCacheTest {

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
    private val cache = BaseStoreOwnerCache()
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
    fun addController_addsController() {
        assertThat(cache.controllers.size, equalTo(controllers.size))
    }

    @Test
    fun getController_returnsProperController() {
        controllers.forEach {
            assertThat(cache.getController(it.first)!! == it.second, equalTo(true))
        }
    }

    @Test
    fun addStore_addsStore() {
        assertThat(cache.stores.size, equalTo(stores.size))
    }

    @Test
    fun getStore_returnsProperStore() {
        stores.forEach {
            assertThat(cache.getStore(it.first)!! == it.second, equalTo(true))
        }
    }

    @Test
    fun onDestroy_callsOnDestroyOnAllControllers() {
        cache.onDestroy()
        controllers.forEach { verify(it.second).onDestroy() }
    }

    @Test
    fun onDestroy_clearsControllers() {
        cache.onDestroy()
        assertThat(cache.controllers.size, equalTo(0))
    }

    @Test
    fun onDestroy_clearsStores() {
        cache.onDestroy()
        assertThat(cache.stores.size, equalTo(0))
    }
}