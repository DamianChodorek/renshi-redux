package com.damianchodorek.renshiredux.store.reducer

import com.damianchodorek.renshiredux.action.FinishingApiCallAction
import com.damianchodorek.renshiredux.action.MakingApiCallAction
import com.damianchodorek.renshiredux.store.MainActivityStore
import com.damianchodorek.renshiredux.store.state.MainActivityState
import com.damianchodorek.renshiredux.utils.RxTestRule
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class FinishingApiCallReducerIntegrationTest {

    @Suppress("unused")
    @get:Rule
    val rxRule = RxTestRule()
    private val store = MainActivityStore()

    @Test
    fun dispatch_doesNothingWhenApiCallsCounterIsZero() {
        dispatchDecrementingAction()
        assertThat(store.state, equalTo(MainActivityState()))
    }

    @Test
    fun dispatch_decrementsApiCallsCounter() {
        dispatchIncrementingAction()
        dispatchIncrementingAction()

        dispatchDecrementingAction()

        assertThat(store.state.apiCallsCount, equalTo(1))
    }

    private fun dispatchIncrementingAction() = store.dispatch(MakingApiCallAction()).subscribe()

    private fun dispatchDecrementingAction() = store.dispatch(FinishingApiCallAction()).subscribe()

    @Test
    fun dispatch_decrementsApiCallsCounter_whenCalledManyTimes() {
        val expectedApiCallsCount = 5

        repeat(expectedApiCallsCount * 2) {
            dispatchIncrementingAction()
        }

        repeat(expectedApiCallsCount) {
            dispatchDecrementingAction()
        }

        assertThat(store.state.apiCallsCount, equalTo(expectedApiCallsCount))
    }

    @Test
    fun dispatch_setsLoadingToFalse_whenCounterIsZero() {
        dispatchIncrementingAction()

        dispatchDecrementingAction()

        assertThat(store.state.loading, equalTo(false))
    }
}