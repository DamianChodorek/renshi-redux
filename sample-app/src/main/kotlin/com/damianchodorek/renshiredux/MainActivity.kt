package com.damianchodorek.renshiredux

import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshiredux.Contract.Plugin.*
import com.damianchodorek.renshiredux.plugin.InitializingPluginImpl
import com.damianchodorek.renshiredux.plugin.MakeApiCallButtonPluginImpl
import com.damianchodorek.renshiredux.plugin.PresentationPluginImpl
import com.damianchodorek.renshiredux.store.MainActivityStore

class MainActivity : BaseActivity() {

    override val store = MainActivityStore()

    init {
        addPlugin(InitializingPluginImpl(this) as InitializingPlugin)
        addPlugin(MakeApiCallButtonPluginImpl(this) as MakeApiCallButtonPlugin)
        addPlugin(PresentationPluginImpl(this) as PresentationPlugin)
    }
}
