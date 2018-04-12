package com.damianchodorek.renshiredux.plugin

import android.os.Bundle
import com.damianchodorek.renshi.plugin.activity.base.SimpleActivityPlugin
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshiredux.Contract.Plugin.InitializingPlugin
import com.damianchodorek.renshiredux.R

/**
 * Initializes [com.damianchodorek.renshiredux.view.MainActivity].
 */
class InitializingPluginImpl(
        acivity: BaseActivity // it can be any activity that inherits from BaseActivity
) : SimpleActivityPlugin(acivity), InitializingPlugin {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // we set content view in plugin
        activity.setContentView(R.layout.activity_main)
    }
}