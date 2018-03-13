package com.damianchodorek.renshiredux.store.reducer

import com.damianchodorek.renshi.store.reducer.Reducer
import com.damianchodorek.renshiredux.action.MakingApiCallAction
import com.damianchodorek.renshiredux.store.state.MainActivityState
import io.reactivex.Single

class MakingApiCallReducer : Reducer<MakingApiCallAction, MainActivityState> {

    override fun reduce(action: MakingApiCallAction, state: MainActivityState): Single<MainActivityState> {
        throw NotImplementedError()
    }
}