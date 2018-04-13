package com.damianchodorek.renshiredux.store

import com.damianchodorek.renshi.store.base.BaseStore
import com.damianchodorek.renshi.store.reducer.CompositeReducer
import com.damianchodorek.renshiredux.action.FinishingApiCallAction
import com.damianchodorek.renshiredux.action.MakingApiCallAction
import com.damianchodorek.renshiredux.store.reducer.FinishingApiCallReducer
import com.damianchodorek.renshiredux.store.reducer.MakingApiCallReducer
import com.damianchodorek.renshiredux.store.state.MainActivityState

/**
 * Store is the glue of all architectural components. It's shared between controllers and presenters
 * so they can communicate without knowing about each other. This way you can develop parts od your app
 * independently.
 * @author Damian Chodorek
 */
class MainActivityStore : BaseStore<MainActivityState>() {

    override val initialState = MainActivityState()

    // We compose many reducers into one root reducer.
    override val stateReducer = CompositeReducer<MainActivityState>().apply {
        // every reducer handles proper action type
        add(MakingApiCallAction::class, MakingApiCallReducer())
        add(FinishingApiCallAction::class, FinishingApiCallReducer())
    }
}