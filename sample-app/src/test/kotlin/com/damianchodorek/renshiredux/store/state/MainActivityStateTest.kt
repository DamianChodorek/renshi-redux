package com.damianchodorek.renshiredux.store.state

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class MainActivityStateTest {

    private val state = MainActivityState()

    @Test
    fun clone_returnsClonedObject() {
        assertThat(state, equalTo(state.clone(lastActionMark = null)))
    }

    @Test
    fun clone_updatesActionMark() {
        val stateWithNewActionMark = state.copy(lastActionMark = "test mark")
        assertThat(stateWithNewActionMark, equalTo(state.clone(lastActionMark = "test mark")))
    }
}