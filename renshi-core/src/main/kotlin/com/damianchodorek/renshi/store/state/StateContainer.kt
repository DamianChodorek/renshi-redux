package com.damianchodorek.renshi.store.state

import io.reactivex.Flowable

interface StateContainer<STATE : State> {

    var state: STATE
    val stateChanges: Flowable<STATE>
}