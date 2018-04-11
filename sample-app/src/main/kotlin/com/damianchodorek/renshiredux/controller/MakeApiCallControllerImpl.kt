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

class MakeApiCallControllerImpl(
        private val interactor: MakeApiCallInteractor = MakeApiCallInteractorImpl(),
        private val log: (Throwable) -> Unit = { it.printStackTrace() }
) : BaseController<MakeApiCallBtnFragmentPlugin, MainActivityState>(), MakeApiCallBtnController {

    private val makeApiCallRequests = PublishSubject.create<Unit>()

    init {
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