package com.damianchodorek.renshiredux.controller

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.renshiredux.Contract.Interactor.MakeApiCallInteractor
import com.damianchodorek.renshiredux.Contract.Plugin.MakeApiCallBtnFragmentPlugin
import com.damianchodorek.renshiredux.Contract.PluginController.MakeApiCallBtnController
import com.damianchodorek.renshiredux.action.FinishingApiCallAction
import com.damianchodorek.renshiredux.action.MakingApiCallAction
import com.damianchodorek.renshiredux.controller.interactor.MakeApiCallInteractorImpl
import com.damianchodorek.renshiredux.store.state.MainActivityState
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject

/**
 * Handles clicks from [com.damianchodorek.renshiredux.plugin.MakeApiCallBtnFragmentPluginImpl.makeApiCallClicks]
 * and starts api call.
 * @param interactor makes fakt api call.
 * @param log error logger.
 */
class MakeApiCallControllerImpl(
        private val interactor: MakeApiCallInteractor = MakeApiCallInteractorImpl(),
        private val log: (Throwable) -> Unit = { it.printStackTrace() }
) : BaseController<MakeApiCallBtnFragmentPlugin, MainActivityState>(), MakeApiCallBtnController {

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