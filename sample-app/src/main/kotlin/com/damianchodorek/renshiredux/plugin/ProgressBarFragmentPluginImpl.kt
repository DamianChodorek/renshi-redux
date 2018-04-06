package com.damianchodorek.renshiredux.plugin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.damianchodorek.renshi.plugin.fragment.base.BaseFragmentPlugin
import com.damianchodorek.renshi.storeowner.BaseFragment
import com.damianchodorek.renshiredux.Contract
import com.damianchodorek.renshiredux.R
import com.damianchodorek.renshiredux.presenter.ProgressBarPresenterImpl

class ProgressBarFragmentPluginImpl(
        fragment: BaseFragment
) : BaseFragmentPlugin(fragment), Contract.Plugin.ProgressBarFragmentPlugin {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_progress_bar, container, false)

    override fun hideLoading() {
        fragment.view!!.visibility = View.GONE
    }

    override fun showLoading() {
        fragment.view!!.visibility = View.VISIBLE
    }

    override fun createController() = ProgressBarPresenterImpl()
}