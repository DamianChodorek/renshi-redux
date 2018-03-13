package com.damianchodorek.renshiredux.plugin

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.plugin.activity.base.BaseActivityPlugin
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshiredux.Contract.Plugin.MakeApiCallButtonPlugin

class MakeApiCallButtonPluginImpl(
        activity: BaseActivity
) : BaseActivityPlugin(activity), MakeApiCallButtonPlugin {

    override fun createController(): Controller? {
        throw NotImplementedError()
    }
}