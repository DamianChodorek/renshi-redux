package com.damianchodorek.renshi.store.reducer

import com.damianchodorek.renshi.action.base.BaseAction
import com.damianchodorek.renshi.store.state.State
import io.reactivex.Single
import org.junit.Test

class CompositeReducerTest {

    private val compositeReducer = CompositeReducer<TestState>()
    private val messagesToAppend = listOf("1", "2", "3", "4")
    private val reducers = mutableListOf<Reducer<BaseAction, TestState>>().apply {
        messagesToAppend.forEach {
            add(createReducer(it))
        }
    }

    private fun createReducer(msgToAppend: String) =
            object : Reducer<BaseAction, TestState> {
                override fun reduce(action: BaseAction, state: TestState) =
                        Single.just(state.copyStateWithAppendedMessage(msgToAppend))

                private fun TestState.copyStateWithAppendedMessage(msgToAppend: String) =
                        copy(message = message + msgToAppend)
            }

    @Test
    fun addAndReduce_returnsExpectedState() {
        reducers.forEach { compositeReducer.add(TestAction::class, it) }

        val testObserver = compositeReducer
                .reduce(TestAction(), TestState(message = ""))
                .test()

        val expectedMsg = messagesToAppend.reduce { left, right -> left + right }
        testObserver.assertResult(TestState(message = expectedMsg))
    }

    @Test
    fun addAndReduce_returnsExpectedState_whenReducersAddedInDifferentOrder() {
        reducers.reversed().forEach { compositeReducer.add(TestAction::class, it) }

        val testObserver = compositeReducer
                .reduce(TestAction(), TestState(message = ""))
                .test()

        val expectedMsg = messagesToAppend.reversed().reduce { left, right -> left + right }
        testObserver.assertResult(TestState(message = expectedMsg))
    }

    @Test
    fun reduce_returnsOriginalState_whenNoReducersForAction() {
        compositeReducer
                .reduce(TestAction(), TestState(message = "asdw"))
                .test()
                .assertResult(TestState(message = "asdw"))
    }

    @Test
    fun reduce_returnsSameStateAsOnlyReducer() {
        compositeReducer.add(TestAction::class, reducers.first())

        val testObserver = compositeReducer
                .reduce(TestAction(), TestState(message = ""))
                .test()

        val expectedMsg = messagesToAppend.first()
        testObserver.assertResult(TestState(message = expectedMsg))
    }
}

private class TestAction : BaseAction()

private data class TestState(
        override val lastActionRenderMark: Any? = null,
        val message: String
) : State {

    override fun clone(lastActionRenderMark: Any?) = copy(lastActionRenderMark = lastActionRenderMark)
}