package com.damianchodorek.sample_simple_app_without_plugins.store

import com.damianchodorek.renshi.store.base.BaseStore
import com.damianchodorek.renshi.store.reducer.CompositeReducer
import com.damianchodorek.sample_simple_app_without_plugins.action.FinishingApiCallAction
import com.damianchodorek.sample_simple_app_without_plugins.action.MakingApiCallAction
import com.damianchodorek.sample_simple_app_without_plugins.store.reducer.FinishingApiCallReducer
import com.damianchodorek.sample_simple_app_without_plugins.store.reducer.MakingApiCallReducer
import com.damianchodorek.sample_simple_app_without_plugins.store.state.MainActivityState

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