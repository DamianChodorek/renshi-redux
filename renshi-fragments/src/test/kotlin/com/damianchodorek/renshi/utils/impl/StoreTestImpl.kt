package com.damianchodorek.renshi.utils.impl

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.base.BaseStore
import com.damianchodorek.renshi.store.reducer.Reducer
import com.nhaarman.mockito_kotlin.mock

/**
 * @author Damian Chodorek
 */
class StoreTestImpl : BaseStore<StateTestImpl>() {

    override val initialState = StateTestImpl()
    override val stateReducer = mock<Reducer<Action, StateTestImpl>>()
}