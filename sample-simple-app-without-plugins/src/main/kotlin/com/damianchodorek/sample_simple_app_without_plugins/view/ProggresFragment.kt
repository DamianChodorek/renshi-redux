package com.damianchodorek.sample_simple_app_without_plugins.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.damianchodorek.renshi.controller.ControllerOwner
import com.damianchodorek.renshi.plugin.PluginDelegate
import com.damianchodorek.renshi.plugin.base.PluginDelegateBuilder
import com.damianchodorek.renshi.store.storeownercache.base.ViewModelCacheProvider
import com.damianchodorek.sample_simple_app_without_plugins.R
import com.damianchodorek.sample_simple_app_without_plugins.presenter.ProgressBarPresenterImpl

/**
 * Responsible for progress bar.
 */
class ProggresFragment : Fragment(), ControllerOwner {

    // we use plugin delegate class to turn our fragment into plugin that has controller
    private val pluginDelegate: PluginDelegate by lazy {
        PluginDelegateBuilder().build(
                storeProvider = { (activity as MainActivity).store },
                storeOwnerCacheProvider = { ViewModelCacheProvider().provide(activity!!) },
                controllerProvider = { createController() },
                pluginProvider = { this }
        )
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_progress_bar, container, false)

    // this attaches controllers
    override fun onStart() {
        super.onStart()
        pluginDelegate.onStartPlugin()
    }

    // this detaches controllers
    override fun onStop() {
        pluginDelegate.onStopPlugin()
        super.onStop()
    }

    fun hideLoading() {
        view!!.visibility = View.GONE
    }

    fun showLoading() {
        view!!.visibility = View.VISIBLE
    }

    override fun createController() = ProgressBarPresenterImpl()
}