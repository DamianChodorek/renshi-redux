package com.damianchodorek.renshi.plugin.activity.base.integration

import com.damianchodorek.renshi.controller.CompositeController
import com.damianchodorek.renshi.utils.integrationImpl.ActivityPluginImpl
import com.damianchodorek.renshi.utils.integrationImpl.StoreTestImpl
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

internal class BaseActivityPluginIntegrationTest : BaseActivityPluginBaseIntegrationTest() {

    @Before
    fun setup() {
        delegate.addPlugin(plugin)
    }

    @Test(expected = UninitializedPropertyAccessException::class)
    fun init_throwsUninitializedPropertyAccessException_whenStoreRefNotSet() {
        controller.getStoreRef()
    }

    @Test
    fun init_controllerHasNullPluginRef() {
        assertThat(controller.getPluginRef(), nullValue())
    }

    @Test
    fun init_controllerIsDetached() {
        assertThat(controller.attached, equalTo(false))
    }

    @Test
    fun onStart_putsStoreToCache() {
        delegate.onStart()

        val expectedStore = cache.getStore(store.getClassName()) as StoreTestImpl
        assertThat(expectedStore, equalTo(store))
    }

    @Test
    fun onStart_putsControllerToCache() {
        delegate.onStart()

        val expectedController = cache.getController(plugin.getClassName()) as CompositeController
        assertThat(expectedController, equalTo(compositeController))
    }

    @Test
    fun onStart_doesNotCreateNewController_whenAlreadyInCache() {
        cache.addController(plugin.getClassName(), controller)

        delegate.onStart()

        verify(controllerProvider, never()).invoke()
    }

    @Test
    fun onStart_setsStoreRefOnController() {
        delegate.onStart()
        assertThat(controller.getStoreRef() as StoreTestImpl, equalTo(store))
    }

    @Test
    fun onStart_setsPluginRefOnController() {
        delegate.onStart()
        assertThat(controller.getPluginRef() as ActivityPluginImpl, equalTo(plugin))
    }

    @Test
    fun onStart_attachesController() {
        delegate.onStart()
        assertThat(controller.attached, equalTo(true))
    }

    @Test
    fun onStart_attachesControllerOnce_whenCalledManyTimes() {
        delegate.onStart()

        prepareControllerToVerifyThatAttachWasCalledOnce()
        delegate.onStart()

        verifyThatAttachWasCalledOnceOnController()
    }

    private fun prepareControllerToVerifyThatAttachWasCalledOnce() {
        controller.attached = false
    }

    private fun verifyThatAttachWasCalledOnceOnController() =
            assertThat(controller.attached, equalTo(false))

    @Test
    fun onStop_detachesController() {
        delegate.onStart()

        delegate.onStop()

        assertThat(controller.attached, equalTo(false))
    }

    @Test
    fun onStop_detachesControllerOnce_whenCalledManyTimes() {
        delegate.onStart()

        delegate.onStop()
        prepareControllerToVerifyThatDetachWasCalledOnce()

        verifyThatDetachWasCalledOnceOnController()
    }

    private fun prepareControllerToVerifyThatDetachWasCalledOnce() {
        controller.attached = true
    }

    private fun verifyThatDetachWasCalledOnceOnController() =
            assertThat(controller.attached, equalTo(true))

    @Test
    fun cacheOnDestroy_destroysController() {
        delegate.onStart()

        cache.onDestroy()

        assertThat(controller.destroyed, equalTo(true))
    }

    @Test
    fun cacheOnDestroy_doesNotDestroyController_whenNoControllerInCache() {
        cache.controllers.clear()
        cache.onDestroy()

        assertThat(controller.destroyed, equalTo(false))
    }

    @Test
    fun cacheOnDestroy_removesControllersFromCache() {
        delegate.onStart()

        cache.onDestroy()

        assertThat(cache.controllers.size, equalTo(0))
    }

    @Test
    fun cacheOnDestroy_removesStoreFromCache() {
        delegate.onStart()

        cache.onDestroy()

        assertThat(cache.stores.size, equalTo(0))
    }

    @Test
    fun onStop_setsControllersPluginRefToNull() {
        delegate.onStart()

        delegate.onStop()

        assertThat(controller.getPluginRef(), nullValue())
    }

    @Test
    fun onStop_disposesControllersDetachObservers() {
        val request = Observable.never<Unit>().test()
        controller.onAttachOperation = {
            controller.disposeOnDetach(request)
        }

        delegate.onStart()
        delegate.onStop()

        assertThat(request.isDisposed, equalTo(true))
    }

    @Test
    fun onStop_doesNotDisposeControllersDestroyObservers() {
        val request = Observable.never<Unit>().test()
        controller.onAttachOperation = {
            controller.disposeOnDestroy(request)
        }

        delegate.onStart()
        delegate.onStop()

        assertThat(request.isDisposed, equalTo(false))
    }

    @Test
    fun cacheOnDestroy_disposesControllersOnDestroyObservers() {
        val request = Observable.never<Unit>().test()
        controller.onAttachOperation = {
            controller.disposeOnDestroy(request)
        }

        delegate.onStart()
        cache.onDestroy()

        assertThat(request.isDisposed, equalTo(true))
    }

    @Test
    fun onStart_controllerModifiesPlugin() {
        controller.onAttachOperation = {
            controller.getPluginRef()!!.testString = "modified"
        }

        delegate.onStart()

        assertThat(plugin.testString, equalTo("modified"))
    }

    @Test
    fun onStart_controllerModifiesPluginInResponseToStoreInitialValue() {
        controller.prepareToModifyPluginOnStoreChanges()

        delegate.onStart()

        assertThat(plugin.testString, equalTo(false.toString()))
    }
}