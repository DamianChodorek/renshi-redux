package com.damianchodorek.renshiredux.controller

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.renshiredux.Contract.Controller.MakeApiCallController
import com.damianchodorek.renshiredux.Contract.Interactor.MakeApiCallInteractor
import com.damianchodorek.renshiredux.Contract.Plugin.MakeApiCallButtonPlugin
import com.damianchodorek.renshiredux.action.FinishingApiCallAction
import com.damianchodorek.renshiredux.action.MakingApiCallAction
import com.damianchodorek.renshiredux.controller.interactor.MakeApiCallInteractorImpl
import com.damianchodorek.renshiredux.store.state.MainActivityState
import io.reactivex.rxkotlin.subscribeBy

class MakeApiCallControllerImpl(
        private val interactor: MakeApiCallInteractor = MakeApiCallInteractorImpl(),
        private val log: (Throwable) -> Unit = { it.printStackTrace() }
) : BaseController<MakeApiCallButtonPlugin, MainActivityState>(), MakeApiCallController {

    override fun onAttachPlugin() {
        disposeOnDestroy(
                plugin!!
                        .makeApiCallClicks
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
}