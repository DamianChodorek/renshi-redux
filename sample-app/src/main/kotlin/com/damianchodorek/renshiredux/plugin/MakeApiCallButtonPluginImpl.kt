package com.damianchodorek.renshiredux.plugin

import android.os.Bundle
import com.damianchodorek.renshi.plugin.activity.base.BaseActivityPlugin
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshiredux.Contract.Plugin.MakeApiCallButtonPlugin
import com.damianchodorek.renshiredux.controller.MakeApiCallControllerImpl
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class MakeApiCallButtonPluginImpl(
        activity: BaseActivity
) : BaseActivityPlugin(activity), MakeApiCallButtonPlugin {

    override var makeApiCallClicks = PublishSubject.create<Unit>()!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity.makeApiCallBtn.setOnClickListener { makeApiCallClicks.onNext(Unit) }
    }

    override fun createController() = MakeApiCallControllerImpl()
}