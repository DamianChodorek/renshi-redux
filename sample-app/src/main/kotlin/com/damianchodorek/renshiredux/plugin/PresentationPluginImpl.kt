package com.damianchodorek.renshiredux.plugin

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.plugin.activity.base.BaseActivityPlugin
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshiredux.Contract.Plugin.PresentationPlugin

class PresentationPluginImpl(
        activity: BaseActivity
) : BaseActivityPlugin(activity), PresentationPlugin {

    override fun createController(): Controller? {
        throw NotImplementedError()
    }

    override fun hideButton() {
        throw NotImplementedError()
    }

    override fun showButton() {
        throw NotImplementedError()
    }

    override fun hideLoading() {
        throw NotImplementedError()
    }

    override fun showLoading() {
        throw NotImplementedError()
    }
}