package com.damianchodorek.renshiredux.store

import com.damianchodorek.renshiredux.store.state.MainActivityState
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class MainActivityStoreTest {

    private val store = MainActivityStore()

    @Test
    fun state_returnsProperInitialState() {
        assertThat(store.state, equalTo(MainActivityState()))
    }
}