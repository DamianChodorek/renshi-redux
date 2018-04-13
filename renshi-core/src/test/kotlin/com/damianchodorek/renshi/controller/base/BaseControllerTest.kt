package com.damianchodorek.renshi.controller.base

import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.state.State
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Test

/**
 * @author Damian Chodorek
 */
class BaseControllerTest {

    private val controller = TestController()
    private val publishSubject = PublishSubject.create<Unit>()

    @Test
    fun plugin_returnsNull_whenPluginIsNotSet() {
        assertNull(controller.getPluginRef())
    }

    @Test
    fun plugin_returnsPlugin_whenPluginIsSet() {
        val pluginMock = mock<BaseActivity>()

        controller.setPluginRef(pluginMock)

        assertThat(controller.getPluginRef()!!, equalTo(pluginMock))
    }

    @Test
    fun store_returnsStore() {
        val storeMock = mock<Store<*>>()

        controller.setStoreRef(storeMock)

        assertThat(controller.getStoreRef(), equalTo(storeMock))
    }

    @Test
    fun onDetachPlugin_clearsDetachDisposable() {
        controller.disposeOnDetach(publishSubject.subscribe())

        controller.onDetachPlugin()

        assertThat(publishSubject.hasObservers(), equalTo(false))
    }

    @Test
    fun onDetachPlugin_doesNotCleanDestroyDisposable() {
        controller.disposeOnDestroy(publishSubject.subscribe())

        controller.onDetachPlugin()

        assertThat(publishSubject.hasObservers(), equalTo(true))
    }

    @Test
    fun onDetachPlugin_setsPluginRefToNull() {
        controller.setPluginRef(mock())

        controller.onDetachPlugin()

        assertNull(controller.getPluginRef())
    }

    @Test
    fun onDestroy_clearsDestroyDisposable() {
        controller.disposeOnDestroy(publishSubject.subscribe())

        controller.onDestroy()

        assertThat(publishSubject.hasObservers(), equalTo(false))
    }

    @Test
    fun onDestroy_doesNotCleanDetachDisposable() {
        controller.disposeOnDetach(publishSubject.subscribe())

        controller.onDestroy()

        assertThat(publishSubject.hasObservers(), equalTo(true))
    }
}

private class TestController : BaseController<BaseActivity, State>() {

    fun getPluginRef() = plugin

    fun getStoreRef() = store

    override fun onAttachPlugin() {
        // implementation not needed in tests
    }
}