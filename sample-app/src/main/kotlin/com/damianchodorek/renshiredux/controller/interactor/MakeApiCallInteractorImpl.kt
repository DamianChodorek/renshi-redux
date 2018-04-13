package com.damianchodorek.renshiredux.controller.interactor

import com.damianchodorek.renshiredux.Contract.Interactor.MakeApiCallInteractor
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Makes fake api call.
 * @author Damian Chodorek
 */
class MakeApiCallInteractorImpl(
        private val scheduler: Scheduler = Schedulers.computation()
) : MakeApiCallInteractor {

    private val apiCallDuration = 4L

    // Waits for a few seconds and completes.
    override fun makeFakeApiCall() =
            Completable.timer(apiCallDuration, TimeUnit.SECONDS, scheduler)!!
}