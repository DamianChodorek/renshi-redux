package com.damianchodorek.renshiredux.plugin

import android.os.Bundle
import com.damianchodorek.renshi.plugin.activity.base.SimpleActivityPlugin
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshiredux.Contract.Plugin.InitializingPlugin
import com.damianchodorek.renshiredux.R

class InitializingPluginImpl(
        acivity: BaseActivity
) : SimpleActivityPlugin(acivity), InitializingPlugin {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity.setContentView(R.layout.activity_main)
    }
}