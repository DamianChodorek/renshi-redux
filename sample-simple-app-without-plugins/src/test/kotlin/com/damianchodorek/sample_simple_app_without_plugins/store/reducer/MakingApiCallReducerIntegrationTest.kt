package com.damianchodorek.sample_simple_app_without_plugins.store.reducer

import com.damianchodorek.sample_simple_app_without_plugins.action.MakingApiCallAction
import com.damianchodorek.sample_simple_app_without_plugins.store.MainActivityStore
import com.damianchodorek.sample_simple_app_without_plugins.utils.RxTestRule
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

/**
 * @author Damian Chodorek
 */
class MakingApiCallReducerIntegrationTest {

    @Suppress("unused")
    @get:Rule
    val rxRule = RxTestRule()
    private val store = MainActivityStore()

    @Test
    fun dispatch_incrementsApiCallsCounter() {
        dispatchAction()
        assertThat(store.state.apiCallsCount, equalTo(1))
    }

    private fun dispatchAction() = store.dispatch(MakingApiCallAction()).subscribe()

    @Test
    fun dispatch_setsLoadingToTrue() {
        dispatchAction()
        assertThat(store.state.loading, equalTo(true))
    }

    @Test
    fun dispatch_incrementsApiCallsCounterAgain_whenCalledManyTimes() {
        val expectedApiCallsCount = 10

        repeat(expectedApiCallsCount) {
            dispatchAction()
        }

        assertThat(store.state.apiCallsCount, equalTo(expectedApiCallsCount))
    }
}