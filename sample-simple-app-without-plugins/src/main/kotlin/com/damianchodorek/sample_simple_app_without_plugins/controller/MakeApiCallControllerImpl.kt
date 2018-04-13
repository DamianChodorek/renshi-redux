package com.damianchodorek.sample_simple_app_without_plugins.controller

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.sample_simple_app_without_plugins.action.FinishingApiCallAction
import com.damianchodorek.sample_simple_app_without_plugins.action.MakingApiCallAction
import com.damianchodorek.sample_simple_app_without_plugins.controller.interactor.MakeApiCallInteractorImpl
import com.damianchodorek.sample_simple_app_without_plugins.store.state.MainActivityState
import com.damianchodorek.sample_simple_app_without_plugins.view.MakeApiCallFragment
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

/**
 * Handles clicks from [com.damianchodorek.sample_simple_app_without_plugins.view.MakeApiCallFragment.makeApiCallClicks]
 * and starts api call.
 * @param interactor makes fakt api call.
 * @param log error logger.
 * @author Damian Chodorek
 */
class MakeApiCallControllerImpl(
        private val interactor: MakeApiCallInteractorImpl = MakeApiCallInteractorImpl(),
        private val log: (Throwable) -> Unit = { it.printStackTrace() }
) : BaseController<MakeApiCallFragment, MainActivityState>() {

    private val makeApiCallRequests = PublishSubject.create<Unit>()

    init {
        // Stream is created in constructor because it should survive detaching from view.
        // We use disposeOnDestroy() to dispose stream when presenter is destroyed permanently.
        disposeOnDestroy(
                makeApiCallRequests
                        .flatMapCompletable {
                            store
                                    .dispatch(MakingApiCallAction())
                                    .andThen(interactor.makeFakeApiCall())
                                    .andThen(store.dispatch(FinishingApiCallAction()))
                        }
                        .subscribeBy(
                                onError = { log(it) }
                        )
        )
    }

    override fun onAttachPlugin() {
        // We use disposeOnDetach() to dispose stream every time plugin is detached.
        disposeOnDetach(
                plugin!!
                        .makeApiCallClicks
                        .subscribeBy(
                                onNext = { makeApiCallRequests.onNext(Unit) },
                                onError = { log(it) }
                        )
        )
    }
}