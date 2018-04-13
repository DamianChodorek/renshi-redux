package com.damianchodorek.renshiredux.view

import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshi.storeowner.BaseFragment
import com.damianchodorek.renshiredux.plugin.MakeApiCallBtnFragmentPluginImpl

/**
 * Responsible for button that is used to make fake api call.
 * @author Damian Chodorek
 */
class MakeApiCallFragment : BaseFragment() {

    /*
    In this sample - fragments share common store with activity. This enables them to communicate
    with each other by rendering/updating the same state.
     */
    override val store: Store<*>
        get() = (activity as BaseActivity).store

    init {
        // Plugins must be added in constructor.
        plug(MakeApiCallBtnFragmentPluginImpl(this))
    }
}
