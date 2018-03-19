package com.damianchodorek.renshiredux.store

import com.damianchodorek.renshi.store.base.BaseStore
import com.damianchodorek.renshi.store.reducer.CompositeReducer
import com.damianchodorek.renshiredux.action.FinishingApiCallAction
import com.damianchodorek.renshiredux.action.MakingApiCallAction
import com.damianchodorek.renshiredux.store.reducer.FinishingApiCallReducer
import com.damianchodorek.renshiredux.store.reducer.MakingApiCallReducer
import com.damianchodorek.renshiredux.store.state.MainActivityState

class MainActivityStore : BaseStore<MainActivityState>() {

    override val initialState = MainActivityState()

    override val stateReducer = CompositeReducer<MainActivityState>().apply {
        add(MakingApiCallAction::class, MakingApiCallReducer())
        add(FinishingApiCallAction::class, FinishingApiCallReducer())
    }
}