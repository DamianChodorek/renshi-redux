package com.damianchodorek.sample_simple_app_without_plugins.store

import com.damianchodorek.sample_simple_app_without_plugins.store.state.MainActivityState
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * @author Damian Chodorek
 */
class MainActivityStoreTest {

    private val store = MainActivityStore()

    @Test
    fun state_returnsProperInitialState() {
        assertThat(store.state, equalTo(MainActivityState()))
    }
}