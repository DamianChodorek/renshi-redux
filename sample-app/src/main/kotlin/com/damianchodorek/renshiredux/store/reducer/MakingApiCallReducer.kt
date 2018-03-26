package com.damianchodorek.renshiredux.store.reducer

import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.renshiredux.action.MakingApiCallAction
import com.damianchodorek.renshiredux.store.state.MainActivityState
import io.reactivex.Single

class MakingApiCallReducer : Reducer<MakingApiCallAction, MainActivityState> {

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

    override fun reduce(action: MakingApiCallAction, state: Boolean) = Single.just(true)
}