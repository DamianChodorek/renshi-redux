package com.damianchodorek.renshiredux.plugin

import android.os.Bundle
import com.damianchodorek.renshi.plugin.fragment.base.BaseFragmentPlugin
import com.damianchodorek.renshi.storeowner.BaseFragment
import com.damianchodorek.renshiredux.Contract.Plugin.MakeApiCallButtonPlugin
import com.damianchodorek.renshiredux.controller.MakeApiCallControllerImpl
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class MakeApiCallButtonPluginImpl(
        fragment: BaseFragment
) : BaseFragmentPlugin(fragment), MakeApiCallButtonPlugin {

    override var makeApiCallClicks = PublishSubject.create<Unit>()!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragment.activity!!.makeApiCallBtn.setOnClickListener { makeApiCallClicks.onNext(Unit) }
    }

    override fun createController() = MakeApiCallControllerImpl()
}