package com.damianchodorek.renshi.utils.impl

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.base.BaseStore
import com.damianchodorek.renshi.store.reducer.Reducer
import com.nhaarman.mockito_kotlin.mock

class TestStoreImpl : BaseStore<TestStateImpl>() {

    override val initialState = TestStateImpl()
    override val stateReducer = mock<Reducer<Action, TestStateImpl>>()
}