package com.damianchodorek.renshiredux.controller

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.renshiredux.Contract.Controller.MakeApiCallController
import com.damianchodorek.renshiredux.Contract.Plugin.MakeApiCallButtonPlugin
import com.damianchodorek.renshiredux.store.state.MainActivityState

class MakeApiCallControllerImpl
    : BaseController<MakeApiCallButtonPlugin, MainActivityState>(), MakeApiCallController {

    override fun onAttachPlugin() {
        throw NotImplementedError()
    }
}