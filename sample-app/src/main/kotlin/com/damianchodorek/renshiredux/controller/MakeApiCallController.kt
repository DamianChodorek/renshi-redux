package com.damianchodorek.renshiredux.controller

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.renshiredux.plugin.MakeApiCallButtonPlugin
import com.damianchodorek.renshiredux.store.state.MainActivityState

class MakeApiCallController : BaseController<MakeApiCallButtonPlugin, MainActivityState>() {

    override fun onAttachPlugin() {
        throw NotImplementedError()
    }
}