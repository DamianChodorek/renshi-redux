package com.damianchodorek.renshi.plugin.activity.base.integration

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.utils.integrationImpl.ComplexAction
import com.damianchodorek.renshi.utils.integrationImpl.SimpleAction
import com.damianchodorek.renshi.utils.integrationImpl.StateTestImpl
import io.reactivex.Single
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

internal class BaseActivityPluginDispatchIntegrationTest : BaseActivityPluginBaseIntegrationTest() {

    @Before
    fun setup() {
        delegate.addPlugin(plugin)
    }

    @Test
    fun onStart_doesNotPropagateStateChange_whenStateNotChanged() {
        controller.onAttachOperation = { dispatch(SimpleAction()) }

        delegate.onStart()

        assertStateValues(store.getInitialStateRef())
    }

    private fun dispatch(action: Action) = controller.getStoreRef().dispatch(action).subscribe()

    private fun assertStateValues(vararg state: StateTestImpl) = storeChanges
            .assertNotComplete()
            .assertNoErrors()
            .assertValueCount(state.size)
            .assertValueSequence(state.toList())

    @Test
    fun onStart_propagatesStateChange_whenStateChanged() {
        val expectedState = StateTestImpl(testProperty = true)
        prepareStateForDispatchOnAction(SimpleAction(), expectedState)

        delegate.onStart()

        assertStateValues(
                store.getInitialStateRef(),
                expectedState
        )
    }

    private fun prepareStateForDispatchOnAction(action: Action, state: StateTestImpl) =
            prepareStatesForDispatchOnActions(listOf(action), listOf(state))

    private fun prepareStatesForDispatchOnActions(actions: List<Action>, states: List<StateTestImpl>) = actions
            .zip(states)
            .forEach {
                store.addReducerResponse(it.first::class, Single.just(it.second))
            }.also {
                controller.onAttachOperation = {
                    actions.forEach { dispatch(it) }
                }
            }

    @Test
    fun onStart_propagatesStateWithNewActionMark() {
        val action = ComplexAction()
        val expectedState = StateTestImpl(testProperty = true, lastActionMark = action.actionMark)
        prepareStateForDispatchOnAction(action, expectedState)

        delegate.onStart()

        assertStateValues(
                store.getInitialStateRef(),
                expectedState
        )
    }

    @Test
    fun onStart_propagatesStateWithNewActionMarkAndThenWithoutActionMark_whenActionIsSigleTime() {
        val action = ComplexAction(singleTime = true)
        val expectedState = StateTestImpl(testProperty = true)
        prepareStateForDispatchOnAction(action, expectedState)

        delegate.onStart()

        assertStateValues(
                store.getInitialStateRef(),
                expectedState.copy(lastActionMark = action.actionMark),
                expectedState
        )
    }

    @Test
    fun onStart_propagatesStateChange_whenManyComposedReducers() {
        val firstState = StateTestImpl(testProperty = true)
        val secondState = StateTestImpl(testProperty = false, lastActionMark = ComplexAction().actionMark)
        prepareStatesForDispatchOnActions(
                listOf(SimpleAction(), ComplexAction()),
                listOf(firstState, secondState)
        )

        delegate.onStart()

        assertStateValues(
                store.getInitialStateRef(),
                firstState,
                secondState
        )
    }

    @Test
    fun onStart_controllerModifiesPluginInResponseToStoreChanges() {
        controller.prepareToModifyPluginOnStoreChanges()
        store.addReducerResponse(SimpleAction::class, Single.just(StateTestImpl(testProperty = true)))

        delegate.onStart()
        dispatch(SimpleAction())

        assertThat(plugin.testString, equalTo(true.toString()))
    }
}