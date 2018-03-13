package com.damianchodorek.renshiredux.store.reducer

import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.renshiredux.action.FinishingApiCallAction
import com.damianchodorek.renshiredux.store.state.MainActivityState
import io.reactivex.Single

class FinishingApiCallReducer : Reducer<FinishingApiCallAction, MainActivityState> {

    override fun reduce(action: FinishingApiCallAction, state: MainActivityState): Single<MainActivityState> {
        throw NotImplementedError()
    }
}