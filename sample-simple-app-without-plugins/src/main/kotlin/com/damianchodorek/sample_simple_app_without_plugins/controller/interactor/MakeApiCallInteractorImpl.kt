package com.damianchodorek.sample_simple_app_without_plugins.controller.interactor

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Makes fake api call.
 */
class MakeApiCallInteractorImpl(
        private val scheduler: Scheduler = Schedulers.computation()
) {

    private val apiCallDuration = 4L

    // Waits for a few seconds and completes.
    fun makeFakeApiCall() = Completable.timer(apiCallDuration, TimeUnit.SECONDS, scheduler)!!
}