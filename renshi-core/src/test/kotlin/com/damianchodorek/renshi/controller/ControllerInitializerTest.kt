package com.damianchodorek.renshi.controller

import com.damianchodorek.renshi.store.Store
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

class ControllerInitializerTest {

    private val initializer = ControllerInitializer()

    @Test
    fun init_savesStoreToPluginCache() {
        val saveStoreToPluginCache = mock<(Store<*>) -> Unit>()
        val store = mock<Store<*>>()

        initializer.init(store, mock(), mock(), mock(), saveStoreToPluginCache)

        verify(saveStoreToPluginCache).invoke(store)
    }

    @Test
    fun init_getsControllerFromPluginCache() {
        val getControllerFromCache = mock<() -> Controller?>()

        initializer.init(mock(), mock(), getControllerFromCache, mock(), mock())

        verify(getControllerFromCache).invoke()
    }

    @Test
    fun init_retursnControllerFromPluginCache_whenCacheContainsController() {
        val controller = mock<Controller>()
        val getControllerFromCache = createControllerProvider(controller)

        val result = initializer.init(mock(), mock(), getControllerFromCache, mock(), mock())

        MatcherAssert.assertThat(result, CoreMatchers.equalTo(controller))
    }

    private fun createControllerProvider(controllerToReturn: Controller?) =
            mock<() -> Controller?>().apply {
                whenever(invoke()).thenReturn(controllerToReturn)
            }

    @Test
    fun init_doesNotCreateNewController_whenCacheContainsController() {
        val getControllerFromCache = createControllerProvider(mock())
        val createController = mock<() -> Controller?>()

        initializer.init(mock(), createController, getControllerFromCache, mock(), mock())

        verifyZeroInteractions(createController)
    }

    @Test
    fun init_createsNewController_whenCacheDoesNotContainController() {
        val createController = mock<() -> Controller?>()

        initializer.init(mock(), createController, mock(), mock(), mock())

        verify(createController).invoke()
    }

    @Test
    fun init_returnsNewController_whenCacheDoesNotContainController() {
        val controller = mock<Controller>()
        val createController = createControllerProvider(controller)

        val result = initializer.init(mock(), createController, mock(), mock(), mock())

        MatcherAssert.assertThat(result, CoreMatchers.equalTo(controller))
    }

    @Test
    fun init_setStoreRefOnNewController_whenCacheDoesNotContainController() {
        val store = mock<Store<*>>()
        val controller = mock<Controller>()
        val createController = createControllerProvider(controller)

        initializer.init(store, createController, mock(), mock(), mock())

        verify(controller).setStoreRef(store)
    }

    @Test
    fun init_savesNewControllerToPluginCache_whenCacheDoesNotContainController() {
        val controller = mock<Controller>()
        val createController = createControllerProvider(controller)
        val saveControllerToStore = mock<(Controller) -> Unit>()

        initializer.init(mock(), createController, mock(), saveControllerToStore, mock())

        verify(saveControllerToStore).invoke(controller)
    }

    @Test
    fun init_doesntSaveNewControllerToPluginCache_whenCacheDoesNotContainControllerAndNoControllerCreated() {
        val createController = mock<() -> Controller?>()
        val saveControllerToStore = mock<(Controller) -> Unit>()

        initializer.init(mock(), createController, mock(), saveControllerToStore, mock())

        verifyZeroInteractions(saveControllerToStore)
    }
}