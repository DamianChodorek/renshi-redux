package com.damianchodorek.renshi.storeowner

import com.damianchodorek.renshi.plugin.Plugable
import com.damianchodorek.renshi.plugin.fragment.DialogFragmentPlugin
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.StoreOwner
import com.pascalwelsch.compositeandroid.fragment.CompositeDialogFragment

@Suppress("RedundantUnitReturnType")
abstract class BaseDialogFragment : CompositeDialogFragment(), StoreOwner, Plugable<DialogFragmentPlugin> {

    abstract override val store: Store<*>

    override fun addPlugin(plugin: DialogFragmentPlugin): Unit {
        try {
            addPlugin(plugin as com.pascalwelsch.compositeandroid.fragment.DialogFragmentPlugin)
        } catch (classCast: ClassCastException) {
            throw IllegalStateException("All fragment plugins must extend SimplDialogFragmentPlugin or BaseDialogFragmentPlugin!")
        }
    }
}