package com.damianchodorek.renshi.storeowner

import com.damianchodorek.renshi.plugin.Plugable
import com.damianchodorek.renshi.plugin.dialogfragment.FragmentPlugin
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.StoreOwner
import com.pascalwelsch.compositeandroid.fragment.CompositeFragment

@Suppress("RedundantUnitReturnType")
abstract class BaseFragment : CompositeFragment(), StoreOwner, Plugable<FragmentPlugin> {

    abstract override val store: Store<*>

    override fun plug(plugin: FragmentPlugin): Unit {
        try {
            addPlugin(plugin as com.pascalwelsch.compositeandroid.fragment.FragmentPlugin)
        } catch (classCast: ClassCastException) {
            throw IllegalStateException("All fragment plugins must extend SimplFragmentPlugin or BaseFragmentPlugin!")
        }
    }
}