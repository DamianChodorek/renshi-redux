package com.damianchodorek.renshi.store

import com.damianchodorek.renshi.action.Action
import com.damianchodorek.renshi.store.state.State
import io.reactivex.Completable
import io.reactivex.Flowable

interface Store<STATE : State> {

    val state: STATE
    val stateChanges: Flowable<STATE>
    fun dispatch(action: Action): Completable
}