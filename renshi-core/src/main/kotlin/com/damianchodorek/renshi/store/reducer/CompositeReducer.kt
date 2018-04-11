package com.damianchodorek.renshi.store.reducer

import com.damianchodorek.renshi.action.Action
import io.reactivex.Single
import kotlin.reflect.KClass

/**
 * Enables to use group of independent reducers as one reducer. You should use this class to
 * split top level reducer to subreducers. Read more at: https://redux.js.org/recipes/structuring-reducers/splitting-reducer-logic
 * @param STATE the type of state that reducer should operate on.
 */
@Suppress("UNCHECKED_CAST")
open class CompositeReducer<STATE> : Reducer<Action, STATE> {

    private val reducers = mutableMapOf<KClass<*>, Reducer<*, STATE>>()

    /**
     * Adds new reducer to composite.
     * @param actionClass type of action that subreducer should respond to.
     * @param reducer subreducer.
     * @return this.
     */
    fun <ACTION : Action> add(
            actionClass: KClass<ACTION>,
            reducer: Reducer<ACTION, STATE>
    ): CompositeReducer<STATE> =
            this.apply {
                if (reducers[actionClass] == null) {
                    reducers[actionClass] = reducer
                } else {
                    reducers[actionClass] = composeReducers(
                            reducers[actionClass] as Reducer<ACTION, STATE>, reducer)
                }
            }

    private fun <ACTION : Action> composeReducers(
            left: Reducer<ACTION, STATE>,
            right: Reducer<ACTION, STATE>
    ): Reducer<ACTION, STATE> =
            object : Reducer<ACTION, STATE> {
                override fun reduce(action: ACTION, state: STATE) =
                        left
                                .reduce(action, state)
                                .flatMap {
                                    right.reduce(action, it)
                                }
            }

    /**
     * Calls [Reducer.reduce] on reducer that is registered to handle specific [action].
     * If no reducer is registered for [action] then old [state] is emitted.
     */
    override fun reduce(action: Action, state: STATE): Single<STATE> =
            Single
                    .just(action::class)
                    .flatMap {
                        if (reducers.containsKey(it)) {
                            (reducers[it]!! as Reducer<Action, STATE>).reduce(action, state)
                        } else {
                            Single.just(state)
                        }
                    }
}