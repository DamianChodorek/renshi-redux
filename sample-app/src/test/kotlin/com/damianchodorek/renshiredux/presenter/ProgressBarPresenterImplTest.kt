package com.damianchodorek.renshiredux.presenter

import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshiredux.Contract.Plugin.ProgressBarFragmentPlugin
import com.damianchodorek.renshiredux.store.MainActivityStore
import com.damianchodorek.renshiredux.store.state.MainActivityState
import com.damianchodorek.renshiredux.utils.RxTestRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.processors.PublishProcessor
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Damian Chodorek
 */
class ProgressBarPresenterImplTest {

    @Suppress("unused")
    @get:Rule
    val rxRule = RxTestRule()

    private val pluginMock = mock<ProgressBarFragmentPlugin>()
    private val stateChanges = PublishProcessor.create<MainActivityState>()
    private val storeMock = mock<Store<MainActivityState>>().apply {
        whenever(dispatch(any())).thenReturn(Completable.complete())
        whenever(stateChanges).thenReturn(this@ProgressBarPresenterImplTest.stateChanges)
    }
    private val logMock = mock<(Throwable) -> Unit>()
    private val presenter = ProgressBarPresenterImpl(logMock).apply {
        setStoreRef(storeMock)
        setPluginRef(pluginMock)
    }

    @Before
    fun setUp() {
        presenter.onAttachPlugin()
    }

    @Test
    fun init_doesNothingWithPlugin() {
        val pluginMock = mock<ProgressBarFragmentPlugin>()

        ProgressBarPresenterImpl().apply {
            setStoreRef(mock())
            setPluginRef(pluginMock)
        }

        verifyZeroInteractions(pluginMock)
    }

    @Test
    fun init_doesNothingWithStore() {
        val storeMock = mock<MainActivityStore>()

        ProgressBarPresenterImpl().apply {
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
    fun stateChanges_showsLoading_whenStateLoading() {
        emitStateLoading()
        verify(pluginMock).showLoading()
    }

    private fun emitStateLoading() = stateChanges.onNext(MainActivityState(loading = true))

    @Test
    fun stateChanges_doesShowLoading_whenTheSameStateEmittedAgain() {
        emitStateLoading()

        emitStateLoading()

        verify(pluginMock).showLoading()
    }

    private fun emitStateNotLoading() = stateChanges.onNext(MainActivityState())

    @Test
    fun stateChanges_hidesLoading_whenStateNotLoading() {
        emitStateChangedToNotLoading()

        verify(pluginMock).hideLoading()
    }

    private fun emitStateChangedToNotLoading() {
        emitStateLoading()
        emitStateNotLoading()
    }

    @Test
    fun stateChanges_doesNotHideLoadingAgain_whenStateNotChanged() {
        emitStateChangedToNotLoading()

        emitStateNotLoading()

        verify(pluginMock).hideLoading()
    }

    @Test
    fun stateChanges_hidesLoadingAgain_whenStateChangedAgain() {
        emitStateChangedToNotLoading()

        emitStateChangedToNotLoading()

        verify(pluginMock, times(2)).hideLoading()
    }
}