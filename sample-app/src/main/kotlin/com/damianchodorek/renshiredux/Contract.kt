package com.damianchodorek.renshiredux

import com.damianchodorek.renshi.plugin.activity.ActivityPlugin
import io.reactivex.Observable

interface Contract {

    interface Plugin {
        interface InitializingPlugin : ActivityPlugin

        interface MakeApiCallButtonPlugin : ActivityPlugin {
            val makeApiCallClicks: Observable<Unit>
        }

        interface RenderingPlugin : ActivityPlugin
    }

    interface Controller {
        interface MakeApiCallController : Controller
        interface RenderingController : Controller
    }

    interface Interactor {
        interface MakeApiCallInteractor
    }
}