package com.damianchodorek.renshi.store.dispatcher

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.base.BaseStore
import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.renshi.store.state.State
import com.damianchodorek.renshi.utils.RxTestRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Flowable
import io.reactivex.Single
import junit.framework.Assert.assertNull
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Damian Chodorek
 */
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
        whenever(reducer.reduce(any(), any())).thenReturn(Single.just(testState))
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
        actionMock.returnRenderMark(renderMarkMock)

        store.dispatch(actionMock).test()

        assertThat(store.state.lastActionMark!!, equalTo(renderMarkMock))
    }

    private fun Action.returnRenderMark(renderMark: Any) {
        whenever(this.actionMark).thenReturn(renderMark)
    }

    @Test
    fun dispatch_resetsLastActionRenderMark() {
        store.dispatch(actionMock).test()

        whenever(actionMock.actionMark).thenReturn(null)
        store.dispatch(actionMock).test()

        assertNull(store.state.lastActionMark)
    }

    @Test
    fun dispatch_updatesState() {
        val stateMock = actionMock.prepareToBeDispatchedWithRenderMark()

        store.dispatch(actionMock).test()

        assertThat(store.state, equalTo(stateMock))
    }

    private fun Action.prepareToBeDispatchedWithRenderMark(): TestState {
        val renderMarkMock = mock<Any>()
        this.returnRenderMark(renderMarkMock)
        return TestState(lastActionMark = renderMarkMock)
    }

    @Test
    fun dispatch_resetsRenderMark_whenActionIsSingleTime() {
        val renderMarkMock = mock<Any>()
        actionMock.returnRenderMark(renderMarkMock)
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
    fun dispatch_usesUpdatedState_whenDispatchedAfterStateChange() {
        val firstState = actionMock.prepareToBeDispatchedWithRenderMark()
        val secondActionMock = mock<Action>()
        secondActionMock.prepareToBeDispatchedWithRenderMark()

        store
                .dispatch(actionMock)
                .andThen(store.dispatch(secondActionMock))
                .test()

        inOrder(reducer).apply {
            verify(reducer).reduce(actionMock, testState)
            verify(reducer).reduce(secondActionMock, firstState)
        }
    }

    @Test
    fun dispatch_dispatchesEventsInProperOrder() {
        Dispatcher.resetSchedulerProvider()
        val (actions, expectedTestStates) = createTestData()
        val testStore = createTestStore()
        val testSubscriber = testStore.stateChanges.test()

        Flowable
                .fromIterable(actions)
                .flatMapCompletable { testStore.dispatch(it) }
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

    private fun createExpectedResults(renderMarks: List<Any>): MutableList<TestState> =
            mutableListOf<TestState>()
                    .apply { add(store.state.copy()) }
                    .apply {
                        renderMarks.forEach {
                            add(store.state.clone(lastActionMark = it))
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

    override fun clone(lastActionMark: Any?) = copy(lastActionMark = lastActionMark)
}