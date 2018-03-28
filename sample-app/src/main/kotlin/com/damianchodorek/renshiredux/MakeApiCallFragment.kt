package com.damianchodorek.renshiredux

import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshi.storeowner.BaseFragment
import com.damianchodorek.renshiredux.Contract.Plugin.MakeApiCallButtonPlugin
import com.damianchodorek.renshiredux.plugin.MakeApiCallButtonPluginImpl

class MakeApiCallFragment : BaseFragment() {

    override val store: Store<*> by lazy { (activity as BaseActivity).store }

    init {
        addPlugin(MakeApiCallButtonPluginImpl(this) as MakeApiCallButtonPlugin)
    }
}
