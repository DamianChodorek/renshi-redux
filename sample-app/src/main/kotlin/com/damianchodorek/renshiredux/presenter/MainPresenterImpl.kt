package com.damianchodorek.renshiredux.presenter

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.renshiredux.Contract.Plugin.PresentationPlugin
import com.damianchodorek.renshiredux.Contract.Presenter.MainPresenter
import com.damianchodorek.renshiredux.store.state.MainActivityState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainPresenterImpl(
        private val log: (Throwable) -> Unit = { it.printStackTrace() }
) : BaseController<PresentationPlugin, MainActivityState>(), MainPresenter {

    override fun onAttachPlugin() {
        disposeOnDetach(
                store
                        .stateChanges
                        .map { it.loading }
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onNext = {
                                    if (it) {
                                        plugin?.hideButton()
                                        plugin?.showLoading()
                                    } else {
                                        plugin?.showButton()
                                        plugin?.hideLoading()
                                    }
                                },
                                onError = { log(it) }
                        )
        )
    }
}