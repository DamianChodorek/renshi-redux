package com.damianchodorek.renshi.store.base

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.renshi.utils.RxTestRule
import com.damianchodorek.renshi.utils.impl.TestStateImpl
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test

class BaseStoreTest {

    @Suppress("unused")
    @get:Rule
    val rxRule = RxTestRule()

    private val initialStateMock = TestStateImpl()
    private val reducerMock = mock<Reducer<Action, TestStateImpl>>()
    private val store = object : BaseStore<TestStateImpl>() {

        override val initialState = initialStateMock
        override val stateReducer = reducerMock
    }

    @Test
    fun init_createBaseStoreContainerWithInitialState() {
        MatcherAssert.assertThat(store.state, CoreMatchers.equalTo(initialStateMock))
    }

    @Test
    fun storeChanges_emitsInitialState() {
        store
                .stateChanges
                .test()
                .assertValue(initialStateMock)
                .assertNotComplete()
                .assertValueCount(1)
    }

    @Test
    fun dispatch_callsReducerAndEmitsNewValue() {
        val renderMark = Any()
        val actionMock = mock<Action>().apply {
            whenever(this.renderMark).thenReturn(renderMark)
        }
        mockReducerResponse(actionMock)

        val testObserver = store.stateChanges.test()
        store.dispatch(actionMock).subscribe()

        testObserver.assertValueSequence(listOf(
                initialStateMock,
                TestStateImpl().copy(lastActionMark = renderMark)
        ))
    }

    private fun mockReducerResponse(actionMock: Action) {
        whenever(reducerMock.reduce(actionMock, initialStateMock))
                .thenReturn(Single.just(initialStateMock.copy()))
    }
}