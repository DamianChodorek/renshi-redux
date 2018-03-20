package com.damianchodorek.renshiredux.controller

import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshiredux.Contract.Interactor.MakeApiCallInteractor
import com.damianchodorek.renshiredux.Contract.Plugin.MakeApiCallButtonPlugin
import com.damianchodorek.renshiredux.action.FinishingApiCallAction
import com.damianchodorek.renshiredux.action.MakingApiCallAction
import com.damianchodorek.renshiredux.store.MainActivityStore
import com.damianchodorek.renshiredux.utils.RxTestRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class MakeApiCallControllerImplTest {

    @Suppress("unused")
    @get:Rule
    val rxRule = RxTestRule()

    private val clicks = PublishSubject.create<Unit>()
    private val pluginMock = mock<MakeApiCallButtonPlugin>().apply {
        whenever(makeApiCallClicks).thenReturn(clicks)
    }
    private val storeMock = mock<Store<*>>().apply {
        whenever(dispatch(any())).thenReturn(Completable.complete())
    }
    private val interactorMock = mock<MakeApiCallInteractor>().apply {
        whenever(makeFakeApiCall()).thenReturn(Completable.complete())
    }
    private val logMock = mock<(Throwable) -> Unit>()
    private val controller = MakeApiCallControllerImpl(
            interactorMock,
            logMock
    ).apply {
        setStoreRef(storeMock)
        setPluginRef(pluginMock)
        onAttachPlugin()
    }

    @Test
    fun init_doesNothingWithPlugin() {
        val pluginMock = mock<MakeApiCallButtonPlugin>()

        MakeApiCallControllerImpl().apply {
            setStoreRef(mock())
            setPluginRef(pluginMock)
        }

        verifyZeroInteractions(pluginMock)
    }

    @Test
    fun init_doesNothingWithStore() {
        val storeMock = mock<MainActivityStore>()

        MakeApiCallControllerImpl().apply {
            setStoreRef(storeMock)
            setPluginRef(mock())
        }

        verifyZeroInteractions(storeMock)
    }

    @Test
    fun onAttachPlugin_observesClicks() {
        assertThat(clicks.hasObservers(), equalTo(true))
    }

    @Test
    fun onAttachPlugin_disposesClicksStream_onDestroy() {
        controller.onDestroy()
        assertThat(clicks.hasObservers(), equalTo(false))
    }

    @Test
    fun makeApiCallClicks_dispatchesMakingApiCallAction() {
        emitClick()
        verify(storeMock).dispatch(any<MakingApiCallAction>())
    }

    private fun emitClick() = clicks.onNext(Unit)

    @Test
    fun makeApiCallClicks_dispatchesMakingApiCallAction_whenClickedManyTimes() {
        val clicksCount = emitClicks()
        verify(storeMock, times(clicksCount)).dispatch(any<MakingApiCallAction>())
    }

    private fun emitClicks(): Int {
        val clicksCount = 5

        repeat(clicksCount) {
            emitClick()
        }

        return clicksCount
    }

    @Test
    fun makeApiCallClicks_makesApiCall() {
        emitClick()
        verify(interactorMock).makeFakeApiCall()
    }

    @Test
    fun makeApiCallClicks_makesApiCall_whenClickedManyTimes() {
        val clicksCount = emitClicks()
        verify(interactorMock, times(clicksCount)).makeFakeApiCall()
    }

    @Test
    fun makeApiCallClicks_dispatchesFinishingApiCallAction() {
        emitClick()
        verify(storeMock).dispatch(any<FinishingApiCallAction>())
    }

    @Test
    fun makeApiCallClicks_dispatchesFinishingApiCallAction_whenClickedManyTimes() {
        val clicksCount = emitClicks()
        verify(storeMock, times(clicksCount)).dispatch(any<FinishingApiCallAction>())
    }

    @Test
    fun makeApiCallClicks_logsError_whenErrorOccurs() {
        val runtimeException = RuntimeException()
        whenever(interactorMock.makeFakeApiCall()).thenThrow(runtimeException)

        emitClick()

        verify(logMock).invoke(runtimeException)
    }
}