package com.damianchodorek.renshiredux.plugin

import android.view.View.GONE
import android.view.View.VISIBLE
import com.damianchodorek.renshi.plugin.activity.base.BaseActivityPlugin
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshiredux.Contract.Plugin.PresentationPlugin
import com.damianchodorek.renshiredux.presenter.MainPresenterImpl
import kotlinx.android.synthetic.main.activity_main.*

class PresentationPluginImpl(
        activity: BaseActivity
) : BaseActivityPlugin(activity), PresentationPlugin {

    override fun hideButton() {
        activity.makeApiCallBtn.visibility = GONE
    }

    override fun showButton() {
        activity.makeApiCallBtn.visibility = VISIBLE
    }

    override fun hideLoading() {
        activity.apiCallProgressBar.visibility = GONE
    }

    override fun showLoading() {
        activity.apiCallProgressBar.visibility = VISIBLE
    }

    override fun createController() = MainPresenterImpl()
}