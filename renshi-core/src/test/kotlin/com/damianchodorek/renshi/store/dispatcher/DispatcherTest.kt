package com.damianchodorek.renshi.store.dispatcher

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.base.BaseStore
import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.renshi.store.state.State
import com.damianchodorek.renshi.utils.RxTestRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class DispatcherTest {

    @Suppress("unused")
    @get:Rule
    val rxRule = RxTestRule()

    private val DEFAULT_MOCKS_NUMBER = 25
    private val testState = TestState()
    private val actionMock = mock<Action>()

    private val reducer = mock<Reducer<Action, TestState>>()
    private val store = createTestStore()

    private fun createTestStore() = object : BaseStore<TestState>() {
        override val initialState = TestState()
        override val stateReducer = reducer
    }

    @Before
    fun setUp() {
        prepareReducerToEmitState(testState)
    }

    private fun prepareReducerToEmitState(state: TestState) {
        whenever(reducer.reduce(any(), any())).thenReturn(Single.just(state))
    }

    @Test
    fun dispatch_callsReduceWithProperParameters() {
        store.dispatch(actionMock).test()
        verify(reducer).reduce(actionMock, testState)
    }

    @Test
    fun dispatch_completes() {
        store.dispatch(actionMock).test().assertComplete()
    }

    @Test
    fun dispatch_completes_whenSingleTimeAction() {
        prepareActionToBeSingleTime()
        store.dispatch(actionMock).test().assertComplete()
    }

    private fun prepareActionToBeSingleTime() {
        whenever(actionMock.singleTime).thenReturn(true)
    }

    @Test
    fun dispatch_updatesLastActionRenderMark() {
        val renderMarkMock = mock<Any>()
        prepareActionToReturnRenderMark(renderMarkMock)

        store.dispatch(actionMock).test()

        MatcherAssert.assertThat(store.state.lastActionMark!!, Matchers.equalTo(renderMarkMock))
    }

    private fun prepareActionToReturnRenderMark(renderMar: Any) {
        whenever(actionMock.actionMark).thenReturn(renderMar)
    }

    @Test
    fun dispatch_resetsLastActionRenderMark() {
        prepareActionToReturnRenderMark(mock())
        store.dispatch(actionMock).test()

        whenever(actionMock.actionMark).thenReturn(null)
        store.dispatch(actionMock).test()

        Assert.assertNull(store.state.lastActionMark)
    }

    @Test
    fun dispatch_updatesState() {
        val renderMarkMock = mock<Any>()
        prepareActionToReturnRenderMark(renderMarkMock)
        val stateMock = TestState(lastActionMark = renderMarkMock)
        prepareReducerToEmitState(stateMock)

        val store = createTestStore()
        store.dispatch(actionMock).test()

        MatcherAssert.assertThat(store.state, Matchers.equalTo(stateMock))
    }

    @Test
    fun dispatch_resetsRenderMark_whenActionIsSingleTime() {
        val renderMarkMock = mock<Any>()
        prepareActionToReturnRenderMark(renderMarkMock)
        prepareActionToBeSingleTime()

        val testSubscriber = store.stateChanges.test()
        store.dispatch(actionMock).subscribe()

        val expectedStates = prepareListOfStates(
                store.state.copy(),
                store.state.copy(lastActionMark = renderMarkMock),
                store.state.copy())
        testSubscriber.assertValueSequence(expectedStates)
    }

    private fun prepareListOfStates(vararg state: TestState) =
            mutableListOf<TestState>().apply {
                state.forEach { add(it) }
            }.toList()

    @Test
    @Ignore("Right now I think that synchronization isn't needed for dispatcher.")
    fun dispatch_dispatchesEventsInProperOrder() {
        RxJavaPlugins.reset()
        val (actions, expectedTestStates) = createTestData()
        val testSubscriber = store.stateChanges.test()

        Flowable.fromIterable(actions)
                .flatMapCompletable { store.dispatch(it) }
                .blockingAwait()

        testSubscriber.assertValueSequence(expectedTestStates)
    }

    private fun createTestData(): Pair<List<Action>, MutableList<TestState>> {
        val renderMarkMocks = createRenderMarkMocks()
        val expectedTestStates = createExpectedResults(renderMarks = renderMarkMocks)
        val actions = createActions(renderMarks = renderMarkMocks)

        return Pair(actions, expectedTestStates)
    }

    private fun createRenderMarkMocks(mocksNumber: Int = DEFAULT_MOCKS_NUMBER) =
            mutableListOf<Any>()
                    .apply {
                        repeat(mocksNumber) { add(mock()) }
                    }.toList()

    private fun createExpectedResults(renderMarks: List<Any>): MutableList<TestState> {
        return mutableListOf<TestState>()
                .apply { add(store.state.copy()) }
                .apply {
                    renderMarks.forEach {
                        add(store.state.clone(lastActionRenderMark = it))
                    }
                }
    }

    private fun createActions(renderMarks: List<Any>, mocksNumber: Int = DEFAULT_MOCKS_NUMBER)
            : List<Action> {

        fun List<Action>.switchRenderMarskToMocks(mocks: List<Any>) =
                zip(mocks).forEach { whenever(it.first.actionMark).thenReturn(it.second) }

        return mutableListOf<Action>()
                .apply {
                    repeat(mocksNumber) { add(mock()) }
                }
                .toList()
                .apply { switchRenderMarskToMocks(mocks = renderMarks) }
    }
}

private data class TestState(override val lastActionMark: Any? = null) : State {

    override fun clone(lastActionRenderMark: Any?) = copy(lastActionMark = lastActionRenderMark)
}