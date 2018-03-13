package com.damianchodorek.renshiredux.controller

import com.damianchodorek.renshi.controller.base.BaseController
import com.damianchodorek.renshiredux.plugin.RenderingPlugin
import com.damianchodorek.renshiredux.store.state.MainActivityState

class RenderingController : BaseController<RenderingPlugin, MainActivityState>() {

    override fun onAttachPlugin() {
        throw NotImplementedError()
    }
}