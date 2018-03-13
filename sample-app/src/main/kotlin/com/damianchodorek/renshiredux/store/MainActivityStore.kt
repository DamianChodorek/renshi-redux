package com.damianchodorek.renshiredux.store

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.base.BaseStore
import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.renshiredux.store.state.MainActivityState

class MainActivityStore : BaseStore<MainActivityState>() {

    override val initialState: MainActivityState
        get() = throw NotImplementedError()

    override val stateReducer: Reducer<Action, MainActivityState>
        get() = throw NotImplementedError()

}