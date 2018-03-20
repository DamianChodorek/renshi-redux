package com.damianchodorek.renshiredux

import com.damianchodorek.renshi.plugin.activity.ActivityPlugin
import io.reactivex.Completable
import io.reactivex.Observable

interface Contract {

    interface Plugin {
        interface InitializingPlugin : ActivityPlugin

        interface MakeApiCallButtonPlugin : ActivityPlugin {
            val makeApiCallClicks: Observable<Unit>
        }

        interface PresentationPlugin : ActivityPlugin {
            fun hideButton()
            fun showButton()
            fun hideLoading()
            fun showLoading()
        }
    }

    interface Controller {
        interface MakeApiCallController : Controller
    }

    interface Interactor {

        interface MakeApiCallInteractor {
            fun makeFakeApiCall(): Completable
        }
    }

    interface Presenter {
        interface MainPresenter : Controller
    }
}