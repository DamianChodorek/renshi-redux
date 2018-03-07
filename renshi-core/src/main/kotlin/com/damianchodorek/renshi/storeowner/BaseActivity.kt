package com.damianchodorek.renshi.storeowner

import com.damianchodorek.renshi.plugin.Plugable
import com.damianchodorek.renshi.plugin.activity.ActivityPlugin
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.StoreOwner
import com.pascalwelsch.compositeandroid.activity.CompositeActivity

@Suppress("RedundantUnitReturnType")
abstract class BaseActivity : CompositeActivity(), StoreOwner, Plugable<ActivityPlugin> {

    abstract override val store: Store<*>

    override fun addPlugin(plugin: ActivityPlugin): Unit {
        try {
            addPlugin(plugin as com.pascalwelsch.compositeandroid.activity.ActivityPlugin)
        } catch (classCast: ClassCastException) {
            throw IllegalStateException("All activity plugins must extend SimpleActivityPlugin or BaseActivityPlugin!")
        }
    }
}