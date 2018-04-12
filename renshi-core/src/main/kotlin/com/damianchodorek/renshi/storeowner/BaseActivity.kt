package com.damianchodorek.renshi.storeowner

import com.damianchodorek.renshi.plugin.Plugable
import com.damianchodorek.renshi.plugin.activity.ActivityPlugin
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.StoreOwner
import com.pascalwelsch.compositeandroid.activity.CompositeActivity

/**
 * Base class for all activities that will be using redux along with plugins and controllers.
 * Extend this class, override store property and add desired plugins.
 */
@Suppress("RedundantUnitReturnType")
abstract class BaseActivity : CompositeActivity(), StoreOwner, Plugable<ActivityPlugin> {

    /**
     * Your store implementation that contains activity state.
     */
    abstract override val store: Store<*>

    /**
     * Adds plugin to activity.
     * @param plugin plugin to add. Must be instance of SimpleActivityPlugin or BaseActivityPlugin.
     * @throws IllegalStateException when plugin is not instance of SimpleActivityPlugin or BaseActivityPlugin.
     */
    override fun plug(plugin: ActivityPlugin): Unit {
        try {
            addPlugin(plugin as com.pascalwelsch.compositeandroid.activity.ActivityPlugin)
        } catch (classCast: ClassCastException) {
            throw IllegalStateException("All activity plugins must extend SimpleActivityPlugin or BaseActivityPlugin!")
        }
    }
}