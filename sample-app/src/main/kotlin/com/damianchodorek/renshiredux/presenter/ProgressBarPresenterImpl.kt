package com.damianchodorek.renshiredux.presenter

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.renshiredux.Contract.Plugin.ProgressBarFragmentPlugin
import com.damianchodorek.renshiredux.Contract.PluginPresenter.ProgressBarPresenter
import com.damianchodorek.renshiredux.store.state.MainActivityState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ProgressBarPresenterImpl(
        private val log: (Throwable) -> Unit = { it.printStackTrace() }
) : BaseController<ProgressBarFragmentPlugin, MainActivityState>(), ProgressBarPresenter {

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
                                    if (it) plugin?.showLoading()
                                    else plugin?.hideLoading()

                                },
                                onError = { log(it) }
                        )
        )
    }
}