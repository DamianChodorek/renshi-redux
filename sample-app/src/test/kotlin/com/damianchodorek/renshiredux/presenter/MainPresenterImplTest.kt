package com.damianchodorek.renshiredux.presenter

import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshiredux.Contract.Plugin.PresentationPlugin
import com.damianchodorek.renshiredux.store.MainActivityStore
import com.damianchodorek.renshiredux.store.state.MainActivityState
import com.damianchodorek.renshiredux.utils.RxTestRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class MainPresenterImplTest {

    //TODO Fix test rule
    @Suppress("unused")
    @get:Rule
    val rxRule = RxTestRule()
    private val pluginMock = mock<PresentationPlugin>()
    private val stateChanges = PublishProcessor.create<MainActivityState>()
    private val storeMock = mock<Store<MainActivityState>>().apply {
        whenever(dispatch(any())).thenReturn(Completable.complete())
        whenever(stateChanges).thenReturn(this@MainPresenterImplTest.stateChanges)
    }
    private val logMock = mock<(Throwable) -> Unit>()
    private val presenter = MainPresenterImpl(logMock).apply {
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        setStoreRef(storeMock)
        setPluginRef(pluginMock)
        onAttachPlugin()
    }

    @Test
    fun init_doesNothingWithPlugin() {
        val pluginMock = mock<PresentationPlugin>()

        MainPresenterImpl().apply {
            setStoreRef(mock())
            setPluginRef(pluginMock)
        }

        verifyZeroInteractions(pluginMock)
    }

    @Test
    fun init_doesNothingWithStore() {
        val storeMock = mock<MainActivityStore>()

        MainPresenterImpl().apply {
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
    fun stateChanges_showsLoading_whenStateLoading() {
        emitStateLoading()
        verify(pluginMock).showLoading()
    }

    @Test
    fun stateChanges_doesShowLoading_whenTheSameStateEmittedAgain() {
        emitStateLoading()

        emitStateLoading()

        verify(pluginMock).showLoading()
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

    @Test
    fun stateChanges_hidesLoading_whenStateNotLoading() {
        emitStateChangedToNotLoading()

        verify(pluginMock).hideLoading()
    }

    @Test
    fun stateChanges_doesNotHideLoadingAgain_whenStateNotChanged() {
        emitStateChangedToNotLoading()

        emitStateNotLoading()

        verify(pluginMock).hideLoading()
    }
}