package com.damianchodorek.renshi.store.reducer

import com.damianchodorek.renshi.action.Action
import io.reactivex.Single

interface Reducer<in ACTION : Action, STATE> {

    fun reduce(action: ACTION, state: STATE): Single<STATE>
}