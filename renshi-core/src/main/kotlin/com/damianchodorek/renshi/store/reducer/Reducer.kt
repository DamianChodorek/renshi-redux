package com.damianchodorek.renshi.store.reducer

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.state.State
import io.reactivex.Single

interface Reducer<in ACTION : Action, STATE : State> {

    fun reduce(action: ACTION, state: STATE): Single<STATE>
}