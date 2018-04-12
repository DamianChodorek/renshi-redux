package com.damianchodorek.sample_simple_app_without_plugins.presenter

import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshiredux.utils.RxTestRule
import com.damianchodorek.sample_simple_app_without_plugins.store.MainActivityStore
import com.damianchodorek.sample_simple_app_without_plugins.store.state.MainActivityState
import com.damianchodorek.sample_simple_app_without_plugins.view.MakeApiCallFragment
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.processors.PublishProcessor
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MakeApiCallBtnPresenterImplTest {

    @Suppress("unused")
    @get:Rule
    val rxRule = RxTestRule()

    private val pluginMock = mock<MakeApiCallFragment>()
    private val stateChanges = PublishProcessor.create<MainActivityState>()
    private val storeMock = mock<Store<MainActivityState>>().apply {
        whenever(dispatch(any())).thenReturn(Completable.complete())
        whenever(stateChanges).thenReturn(this@MakeApiCallBtnPresenterImplTest.stateChanges)
    }
    private val logMock = mock<(Throwable) -> Unit>()
    private val presenter = MakeApiCallBtnPresenterImpl(logMock).apply {
        setStoreRef(storeMock)
        setPluginRef(pluginMock)
    }

    @Before
    fun setUp() {
        presenter.onAttachPlugin()
    }

    @Test
    fun init_doesNothingWithPlugin() {
        val pluginMock = mock<MakeApiCallFragment>()

        MakeApiCallBtnPresenterImpl().apply {
            setStoreRef(mock())
            setPluginRef(pluginMock)
        }

        verifyZeroInteractions(pluginMock)
    }

    @Test
    fun init_doesNothingWithStore() {
        val storeMock = mock<MainActivityStore>()

        MakeApiCallBtnPresenterImpl().apply {
            setStoreRef(storeMock)
            setPluginRef(mock())
        }

        verifyZeroInteractions(storeMock)
    }

    @Test
    fun onAttachPlugin_subscribesToStateChanges() {
        assertThat(stateChanges.hasSubscribers(), equalTo(true))
    }

    @Test
    fun onDetachPlugin_doesNotObserveStateChanges() {
        presenter.onDetachPlugin()
        assertThat(stateChanges.hasSubscribers(), equalTo(false))
    }

    @Test
    fun stateChanges_doesNotPresentState_whenInitialStateEmitted() {
        verifyZeroInteractions(pluginMock)
    }

    @Test
    fun stateChanges_logsError_whenErrorOccurs() {
        val runtimeException = RuntimeException()

        stateChanges.onError(runtimeException)

        verify(logMock).invoke(runtimeException)
    }

    @Test
    fun stateChanges_hidesButton_whenStateLoading() {
        emitStateLoading()
        verify(pluginMock).hideButton()
    }

    private fun emitStateLoading() = stateChanges.onNext(MainActivityState(loading = true))

    @Test
    fun stateChanges_doesNotHideButton_whenTheSameStateEmittedAgain() {
        emitStateLoading()

        emitStateLoading()

        verify(pluginMock).hideButton()
    }

    @Test
    fun stateChanges_showsButton_whenStateNotLoading() {
        emitStateChangedToNotLoading()
        verify(pluginMock).showButton()
    }

    private fun emitStateChangedToNotLoading() {
        emitStateLoading()
        emitStateNotLoading()
    }

    private fun emitStateNotLoading() = stateChanges.onNext(MainActivityState())

    @Test
    fun stateChanges_doesNotShowButtonAgain_whenStateNotChanged() {
        emitStateChangedToNotLoading()

        emitStateNotLoading()

        verify(pluginMock).showButton()
    }
}