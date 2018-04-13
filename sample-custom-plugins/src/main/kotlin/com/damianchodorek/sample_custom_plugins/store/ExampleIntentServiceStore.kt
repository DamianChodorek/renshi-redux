package com.damianchodorek.sample_custom_plugins.store

import com.damianchodorek.renshi.store.base.BaseStore
import com.damianchodorek.renshi.store.reducer.CompositeReducer
import com.damianchodorek.sample_custom_plugins.store.state.ExampleIntentServiceState

/**
 * Service store that holds the state.
 * @author Damian Chodorek
 */
class ExampleIntentServiceStore : BaseStore<ExampleIntentServiceState>() {

    override val initialState = ExampleIntentServiceState(null)

    /**
     * We don't register any reducers because this example shows only how to structure your classes.
     */
    override val stateReducer = CompositeReducer<ExampleIntentServiceState>()
}