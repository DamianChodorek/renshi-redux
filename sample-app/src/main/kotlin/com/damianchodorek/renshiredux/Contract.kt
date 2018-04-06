package com.damianchodorek.renshiredux

import com.damianchodorek.renshi.plugin.activity.ActivityPlugin
import com.damianchodorek.renshi.plugin.dialogfragment.FragmentPlugin
import io.reactivex.Completable
import io.reactivex.Observable

interface Contract {

    interface Plugin {
        interface InitializingPlugin : ActivityPlugin

        interface MakeApiCallBtnFragmentPlugin : FragmentPlugin {
            val makeApiCallClicks: Observable<Unit>
            fun hideButton()
            fun showButton()
        }

        interface ProgressBarFragmentPlugin : FragmentPlugin {
            fun hideLoading()
            fun showLoading()
        }
    }

    interface Controller {
        interface MakeApiCallBtnController : Controller
    }

    interface Presenter {
        interface MakeApiCallBtnPresenter : Controller
        interface ProgressBarPresenter : Controller
    }

    interface Interactor {
        interface MakeApiCallInteractor {
            fun makeFakeApiCall(): Completable
        }
    }
}