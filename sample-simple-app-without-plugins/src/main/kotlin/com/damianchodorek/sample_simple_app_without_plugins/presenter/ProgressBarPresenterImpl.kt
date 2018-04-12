package com.damianchodorek.sample_simple_app_without_plugins.presenter

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.sample_simple_app_without_plugins.store.state.MainActivityState
import com.damianchodorek.sample_simple_app_without_plugins.view.ProggresFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Listens to state changes and performs appropriate logic on progress plugin.
 * @param log logs errors.
 */
class ProgressBarPresenterImpl(
        private val log: (Throwable) -> Unit = { it.printStackTrace() }
) : BaseController<ProggresFragment, MainActivityState>() {

    override fun onAttachPlugin() {
        disposeOnDetach(
                store
                        .stateChanges
                        .map { it.loading }
                        .distinctUntilChanged() // listen to changes only for proper state part (loading flag)
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