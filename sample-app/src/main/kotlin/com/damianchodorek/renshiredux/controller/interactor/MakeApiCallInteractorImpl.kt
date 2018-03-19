package com.damianchodorek.renshiredux.controller.interactor

import com.damianchodorek.renshiredux.Contract.Interactor.MakeApiCallInteractor
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MakeApiCallInteractorImpl(
        private val scheduler: Scheduler = Schedulers.computation()
) : MakeApiCallInteractor {

    private val apiCallDuration = 4L

    override fun makeFakeApiCall() =
            Completable.timer(apiCallDuration, TimeUnit.SECONDS, scheduler)!!
}