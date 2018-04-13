package com.damianchodorek.sample_simple_app_without_plugins.presenter

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.sample_simple_app_without_plugins.store.state.MainActivityState
import com.damianchodorek.sample_simple_app_without_plugins.view.MakeApiCallFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Listens to state changes and performs appropriate logic on button plugin.
 * @param log logs errors.
 * @author Damian Chodorek
 */
class MakeApiCallBtnPresenterImpl(
        private val log: (Throwable) -> Unit = { it.printStackTrace() }
) : BaseController<MakeApiCallFragment, MainActivityState>() {

    override fun onAttachPlugin() {
        // we only want to listen for store changes when plugin is attached
        disposeOnDetach(
                store
                        .stateChanges
                        .map { it.loading }
                        .distinctUntilChanged() // listen to changes only for proper state part (loading flag)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onNext = {
                                    if (it) plugin?.hideButton()
                                    else plugin?.showButton()
                                },
                                onError = { log(it) }
                        )
        )
    }
}