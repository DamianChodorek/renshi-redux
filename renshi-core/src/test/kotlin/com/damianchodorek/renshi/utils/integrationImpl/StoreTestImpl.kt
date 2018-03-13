package com.damianchodorek.renshi.utils.integrationImpl

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.base.BaseStore
import com.damianchodorek.renshi.store.reducer.CompositeReducer
import com.damianchodorek.renshi.store.reducer.Reducer
import io.reactivex.Single
import kotlin.reflect.KClass

internal class StoreTestImpl : BaseStore<StateTestImpl>() {

    override val initialState = StateTestImpl()
    override val stateReducer = CompositeReducer<StateTestImpl>()

    fun getClassName() = this::class.java.name!!

    fun getInitialStateRef() = initialState

    fun <ACTION : Action> addReducerResponse(
            actionClass: KClass<ACTION>,
            reducerResponse: Single<StateTestImpl>
    ) = stateReducer.add(
            actionClass,
            object : Reducer<ACTION, StateTestImpl> {
                override fun reduce(action: ACTION, state: StateTestImpl) = reducerResponse
            })
}