package com.damianchodorek.sample_simple_app_without_plugins.store.reducer

import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.sample_simple_app_without_plugins.action.FinishingApiCallAction
import com.damianchodorek.sample_simple_app_without_plugins.store.state.MainActivityState
import io.reactivex.Single

/**
 * Changes state when api call is finished.
 * Reducer may be split into slice reducers as in [MakingApiCallReducer].
 * @author Damian Chodorek
 */
class FinishingApiCallReducer : Reducer<FinishingApiCallAction, MainActivityState> {

    override fun reduce(action: FinishingApiCallAction, state: MainActivityState) =
            Single
                    .just(state)
                    .map {
                        if (it.apiCallsCount > 0)
                            it.copy(
                                    apiCallsCount = it.apiCallsCount - 1,
                                    loading = it.calculateNewLoadingVal()
                            )
                        else it
                    }!!

    private fun MainActivityState.calculateNewLoadingVal() =
            if (apiCallsCount == 1) false
            else loading
}