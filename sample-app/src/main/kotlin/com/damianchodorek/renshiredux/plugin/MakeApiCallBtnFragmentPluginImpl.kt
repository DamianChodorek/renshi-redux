package com.damianchodorek.renshiredux.plugin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.damianchodorek.renshi.controller.CompositeController
import com.damianchodorek.renshi.plugin.fragment.base.BaseFragmentPlugin
import com.damianchodorek.renshi.storeowner.BaseFragment
import com.damianchodorek.renshiredux.Contract.Plugin.MakeApiCallBtnFragmentPlugin
import com.damianchodorek.renshiredux.R
import com.damianchodorek.renshiredux.controller.MakeApiCallControllerImpl
import com.damianchodorek.renshiredux.presenter.MakeApiCallBtnPresenterImpl
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_make_api_call.view.*

class MakeApiCallBtnFragmentPluginImpl(
        fragment: BaseFragment
) : BaseFragmentPlugin(fragment), MakeApiCallBtnFragmentPlugin {

    override var makeApiCallClicks = PublishSubject.create<Unit>()!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_make_api_call, container, false).apply {
        makeApiCallBtn.setOnClickListener { makeApiCallClicks.onNext(Unit) }
    }

    override fun hideButton() {
        fragment.view!!.visibility = View.GONE
    }

    override fun showButton() {
        fragment.view!!.visibility = View.VISIBLE
    }

    override fun createController() = CompositeController(
            listOf(MakeApiCallControllerImpl(), MakeApiCallBtnPresenterImpl())
    )
}