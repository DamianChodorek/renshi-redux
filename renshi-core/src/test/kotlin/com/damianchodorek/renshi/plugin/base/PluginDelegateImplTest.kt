package com.damianchodorek.renshi.plugin.base

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.controller.ControllerInitializer
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache
import com.nhaarman.mockito_kotlin.*
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * @author Damian Chodorek
 */
class PluginDelegateImplTest {

    private val captors = InitializerCaptors()
    private val pluginNameProvider = { "random name" }
    private val controllerMock = mock<Controller>()
    private val cacheMock = mock<StoreOwnerCache>().apply {
        whenever(getController(pluginNameProvider())).thenReturn(controllerMock)
    }
    private val storeProvider = mock<() -> Store<*>>()
    private val storeOwnerCacheProvider = mock<() -> StoreOwnerCache>().apply {
        whenever(invoke()).thenReturn(cacheMock)
    }
    private val controllerProvider = mock<() -> Controller?>()
    private val pluginProvider = mock<() -> Any>()
    @Mock
    private lateinit var controllerInitializer: ControllerInitializer

    @InjectMocks private var delegate = PluginDelegateImpl(
            storeProvider,
            storeOwnerCacheProvider,
            controllerProvider,
            pluginProvider,
            pluginNameProvider
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun onStartPlugin_initsControllerUsingProperStore() {
        val storeMock = mock<Store<*>>()
        whenever(storeProvider.invoke()).thenReturn(storeMock)
        prepareControllerInitializer()

        delegate.onStartPlugin()

        MatcherAssert.assertThat(captors.store.firstValue, CoreMatchers.equalTo(storeMock))
    }

    private fun prepareControllerInitializer(controllerToReturn: Controller? = controllerMock) =
            captors.apply {
                whenever(controllerInitializer.init(
                        store.capture(),
                        createController.capture(),
                        getControllerFromPluginCache.capture(),
                        saveControllerToPluginCache.capture(),
                        saveStoreToPluginCache.capture()
                )).thenReturn(controllerToReturn)
            }

    @Test
    fun onStartPlugin_initsControllerUsingProperControllerCreator() {
        prepareControllerInitializer()

        delegate.onStartPlugin()

        MatcherAssert.assertThat(captors.createController.firstValue, CoreMatchers.equalTo(controllerProvider))
    }

    @Test
    fun onStartPlugin_initsControllerUsingProperControllerCacheProvider() {
        prepareControllerInitializer()

        delegate.onStartPlugin()

        MatcherAssert.assertThat(captors.getControllerFromPluginCache.firstValue(), CoreMatchers.equalTo(controllerMock))
    }

    @Test
    fun onStartPlugin_initsControllerUsingProperControllerCacheSaving() {
        prepareControllerInitializer()

        delegate.onStartPlugin()

        captors.saveControllerToPluginCache.firstValue(controllerMock)
        verify(cacheMock).addController(pluginNameProvider(), controllerMock)
    }

    @Test
    fun onStartPlugin_initsControllerUsingProperStoreCacheSaving() {
        prepareControllerInitializer()
        val storeMock = mock<Store<*>>()
        whenever(storeProvider.invoke()).thenReturn(storeMock)

        delegate.onStartPlugin()

        captors.saveStoreToPluginCache.firstValue(storeMock)
        verify(cacheMock).addStore(storeMock::class.java.name, storeMock)
    }

    @Test
    fun onStartPlugin_setsPluginRefOnController() {
        val pluginMock = Any()
        whenever(pluginProvider()).thenReturn(pluginMock)
        prepareControllerInitializer()

        delegate.onStartPlugin()

        verify(controllerMock).setPluginRef(pluginMock)
    }

    @Test
    fun onStartPlugin_doesNotSetPluginRefOnController_whenNoController() {
        prepareControllerInitializer(null)

        delegate.onStartPlugin()

        verifyZeroInteractions(controllerMock)
    }

    @Test
    fun onStartPlugin_attachesController() {
        prepareControllerInitializer()

        delegate.onStartPlugin()

        verify(controllerMock).onAttachPlugin()
    }

    @Test
    fun onStartPlugin_doesNotAttacheController_whenNoController() {
        prepareControllerInitializer(null)

        delegate.onStartPlugin()

        verifyZeroInteractions(controllerMock)
    }

    @Test
    fun onStartPlugin_doesNotStartAgain_whenAlreadyAttached() {
        whenever(storeProvider.invoke()).thenReturn(mock())
        prepareControllerInitializer(null)

        repeat(10) { delegate.onStartPlugin() }

        verify(controllerInitializer).init(any(), any(), any(), any(), any())
    }

    @Test
    fun onStopPlugin_doesNotDetacheController_whenDelegateNotStarted() {
        delegate.onStopPlugin()
        verify(controllerMock, times(0)).onDetachPlugin()
    }

    @Test
    fun onStopPlugin_detachesController_whenDelegateStarted() {
        prepareControllerInitializer()
        delegate.onStartPlugin()

        delegate.onStopPlugin()

        verify(controllerMock).onDetachPlugin()
    }

    @Test
    fun onStopPlugin_doesNotStopAgain_whenAlreadyStopped() {
        prepareControllerInitializer()
        delegate.onStartPlugin()

        repeat(10) { delegate.onStopPlugin() }

        verify(controllerMock).onDetachPlugin()
    }
}

private class InitializerCaptors {
    val store = argumentCaptor<Store<*>>()
    val createController = argumentCaptor<() -> Controller?>()
    val getControllerFromPluginCache = argumentCaptor<() -> Controller?>()
    val saveControllerToPluginCache = argumentCaptor<(Controller) -> Unit>()
    val saveStoreToPluginCache = argumentCaptor<(Store<*>) -> Unit>()
}