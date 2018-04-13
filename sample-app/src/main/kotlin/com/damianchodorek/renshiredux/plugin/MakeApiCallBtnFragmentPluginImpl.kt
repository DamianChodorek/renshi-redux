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

/**
 * Starts API call after button click.
 * @author Damian Chodorek
 */
class MakeApiCallBtnFragmentPluginImpl(
        fragment: BaseFragment //it can be any fragment that inherits from BaseFragment
) : BaseFragmentPlugin(fragment), MakeApiCallBtnFragmentPlugin {

    /**
     * Emits event on every button press.
     */
    override val makeApiCallClicks = PublishSubject.create<Unit>()!!

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

    /*
    We compose two independent controllers into one. You can split your controller logic as you wish.
     */
    override fun createController() = CompositeController(
            listOf(MakeApiCallControllerImpl(), MakeApiCallBtnPresenterImpl())
    )
}