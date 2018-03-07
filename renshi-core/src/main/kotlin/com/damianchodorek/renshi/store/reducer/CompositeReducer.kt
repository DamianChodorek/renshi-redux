package com.damianchodorek.renshi.store.reducer

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.state.State
import io.reactivex.Single
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
open class CompositeReducer<STATE : State> : Reducer<Action, STATE> {

    private val reducers = mutableMapOf<KClass<*>, Reducer<*, STATE>>()

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