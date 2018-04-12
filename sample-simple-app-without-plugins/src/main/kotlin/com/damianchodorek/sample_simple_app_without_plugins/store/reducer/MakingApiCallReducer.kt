package com.damianchodorek.sample_simple_app_without_plugins.store.reducer

import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.sample_simple_app_without_plugins.action.MakingApiCallAction
import com.damianchodorek.sample_simple_app_without_plugins.store.state.MainActivityState
import io.reactivex.Single

/**
 * Changes state when api call is started. It uses slice reducers to outsource logic of changing
 * proper state parts. It is overkill to do it here, but it is for education purposes.
 */
class MakingApiCallReducer : Reducer<MakingApiCallAction, MainActivityState> {

    // slice reducers for every state field
    private val apicallCountSliceReducer = ApiCallsCountSliceReducer()
    private val loadingSliceReducer = LoadingSliceReducer()

    override fun reduce(action: MakingApiCallAction, state: MainActivityState) =
            Single
                    .just(state)
                    .map {
                        it.copy(
                                apiCallsCount = apicallCountSliceReducer
                                        .reduce(action, it.apiCallsCount)
                                        .blockingGet(),
                                loading = loadingSliceReducer
                                        .reduce(action, it.loading)
                                        .blockingGet()
                        )
                    }!!
}

private class ApiCallsCountSliceReducer : Reducer<MakingApiCallAction, Int> {

    override fun reduce(action: MakingApiCallAction, state: Int) = Single.just(state + 1)!!
}

private class LoadingSliceReducer : Reducer<MakingApiCallAction, Boolean> {

    override fun reduce(action: MakingApiCallAction, state: Boolean) = Single.just(true)!!
}