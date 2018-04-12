package com.damianchodorek.renshiredux.store.reducer

import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.renshiredux.action.FinishingApiCallAction
import com.damianchodorek.renshiredux.store.state.MainActivityState
import io.reactivex.Single

/**
 * Changes state when api call is finished.
 * Reducer may be split into slice reducers as in [MakingApiCallReducer].
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