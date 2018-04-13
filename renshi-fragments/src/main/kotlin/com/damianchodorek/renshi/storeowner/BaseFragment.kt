package com.damianchodorek.renshi.storeowner

import com.damianchodorek.renshi.plugin.Plugable
import com.damianchodorek.renshi.plugin.dialogfragment.FragmentPlugin
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.StoreOwner
import com.pascalwelsch.compositeandroid.fragment.CompositeFragment

/**
 * Base class for all dialog fragments that will be using redux along with plugins and controllers.
 * Extend this class, override store property and add desired plugins.
 * @author Damian Chodorek
 */
@Suppress("RedundantUnitReturnType")
abstract class BaseFragment : CompositeFragment(), StoreOwner, Plugable<FragmentPlugin> {

    /**
     * Your store implementation that contains fragment state.
     * Note that to share state with activity you may implement this like:
     * ```
     * override val store: Store<*>
     *     get() = (activity as BaseActivity).store
     * ```
     */
    abstract override val store: Store<*>

    /**
     * Adds plugin to activity.
     * @param plugin plugin to add. Must be instance of
     * [com.damianchodorek.renshi.plugin.fragment.base.SimpleFragmentPlugin]
     * or [[com.damianchodorek.renshi.plugin.fragment.base.BaseFragmentPlugin].
     * @throws IllegalStateException when plugin is not instance of
     * [com.damianchodorek.renshi.plugin.fragment.base.SimpleFragmentPlugin]
     * or [[com.damianchodorek.renshi.plugin.fragment.base.BaseFragmentPlugin].
     */
    override fun plug(plugin: FragmentPlugin): Unit {
        try {
            addPlugin(plugin as com.pascalwelsch.compositeandroid.fragment.FragmentPlugin)
        } catch (classCast: ClassCastException) {
            throw IllegalStateException("All fragment plugins must extend SimplFragmentPlugin or BaseFragmentPlugin!")
        }
    }
}