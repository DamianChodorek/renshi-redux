package com.damianchodorek.renshiredux.view

import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshiredux.Contract.Plugin.InitializingPlugin
import com.damianchodorek.renshiredux.plugin.InitializingPluginImpl
import com.damianchodorek.renshiredux.store.MainActivityStore

class MainActivity : BaseActivity() {

    override val store = MainActivityStore()

    init {
        addPlugin(InitializingPluginImpl(this) as InitializingPlugin)
    }
}