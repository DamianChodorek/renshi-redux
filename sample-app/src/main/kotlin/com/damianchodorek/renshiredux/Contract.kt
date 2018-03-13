package com.damianchodorek.renshiredux

import com.damianchodorek.renshi.plugin.activity.ActivityPlugin

interface Contract {

    interface Plugin {
        interface InitializingPlugin : ActivityPlugin
        interface MakeApiCallButtonPlugin : ActivityPlugin
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