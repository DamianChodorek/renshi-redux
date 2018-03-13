package com.damianchodorek.renshiredux.plugin

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.plugin.activity.base.BaseActivityPlugin
import com.damianchodorek.renshiredux.MainActivity

class MakeApiCallButtonPlugin(
        activity: MainActivity
) : BaseActivityPlugin(activity) {

    override fun createController(): Controller? {
        throw NotImplementedError()
    }
}