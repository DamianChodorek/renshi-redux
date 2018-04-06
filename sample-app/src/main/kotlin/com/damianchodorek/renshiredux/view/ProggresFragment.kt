package com.damianchodorek.renshiredux.view

import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshi.storeowner.BaseFragment
import com.damianchodorek.renshiredux.Contract.Plugin.ProgressBarFragmentPlugin
import com.damianchodorek.renshiredux.plugin.ProgressBarFragmentPluginImpl

class ProggresFragment : BaseFragment() {

    override val store: Store<*>
        get() = (activity as BaseActivity).store

    init {
        addPlugin(ProgressBarFragmentPluginImpl(this) as ProgressBarFragmentPlugin)
    }
}