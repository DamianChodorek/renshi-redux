package com.damianchodorek.renshiredux.store.reducer

import com.damianchodorek.renshiredux.action.MakingApiCallAction
import com.damianchodorek.renshiredux.store.MainActivityStore
import com.damianchodorek.renshiredux.store.state.MainActivityState
import com.damianchodorek.renshiredux.utils.RxTestRule
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class MakingApiCallReducerIntegrationTest {

    @Suppress("unused")
    @get:Rule
    val rxRule = RxTestRule()
    private val store = MainActivityStore()

    @Test
    fun dispatch_incrementsStateApiCallsCounter() {
        dispatchAction()
        assertThat(store.state, equalTo(MainActivityState(apiCallsCount = 1)))
    }

    private fun dispatchAction() = store.dispatch(MakingApiCallAction()).subscribe()

    @Test
    fun dispatch_incrementsStateApiCallsCounter_whenCalledManyTimes() {
        val expectedApiCallsCount = 10

        repeat(expectedApiCallsCount) {
            dispatchAction()
        }

        val expectedState = MainActivityState(apiCallsCount = expectedApiCallsCount)
        assertThat(store.state, equalTo(expectedState))
    }
}