package com.damianchodorek.renshiredux.controller

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.renshiredux.Contract.Controller.RenderingController
import com.damianchodorek.renshiredux.Contract.Plugin.RenderingPlugin
import com.damianchodorek.renshiredux.store.state.MainActivityState

class RenderingControllerImpl
    : BaseController<RenderingPlugin, MainActivityState>(), RenderingController {

    override fun onAttachPlugin() {
        throw NotImplementedError()
    }
}