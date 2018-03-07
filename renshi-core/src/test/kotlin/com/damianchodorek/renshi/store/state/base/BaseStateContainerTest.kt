package com.damianchodorek.renshi.store.state.base

import com.damianchodorek.renshi.store.state.State
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class BaseStateContainerTest {

    private val initialValue = mock<State>()
    val store = BaseStateContainer(initialValue)

    @Test
    fun state_returnsInitialState() {
        assertStateEqualsTo(initialValue)
    }

    private fun assertStateEqualsTo(value: State) = assertThat(store.state, CoreMatchers.equalTo(value))

    @Test
    fun storeChanges_emitsInitialValue_whenSubscribe() {
        store
                .stateChanges
                .test()
                .assertValue(initialValue)
    }

    @Test
    fun storeChanges_emitsInitialValue_whenSubsequentSubscribes() {
        repeat(10) {
            store
                    .stateChanges
                    .test()
                    .assertValue(initialValue)
        }
    }

    @Test
    fun storeChanges_emitsNewValue() {
        val testSubscriber = store.stateChanges.test()

        val newState = mock<State>()
        store.state = newState

        testSubscriber
                .assertValueSequence(listOf(
                        initialValue,
                        newState
                ))
    }

    @Test
    fun state_returnsNewValue() {
        val newState = mock<State>()

        store.state = newState

        assertStateEqualsTo(newState)
    }

    @Test
    fun storeChanges_emitsNewValue_onSubsequentSubscribes() {
        val newState = mock<State>()

        store.state = newState

        repeat(10) {
            store
                    .stateChanges
                    .test()
                    .assertValue(newState)
        }
    }

    @Test
    fun storeChanges_doesntEmitTheSameValue() {
        val testSubscriber = store.stateChanges.test()

        val newState = mock<State>()
        store.state = newState
        store.state = newState

        testSubscriber
                .assertValueSequence(listOf(
                        initialValue,
                        newState
                ))
    }
}