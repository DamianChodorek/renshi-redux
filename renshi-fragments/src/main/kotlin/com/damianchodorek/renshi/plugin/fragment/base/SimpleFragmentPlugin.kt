package com.damianchodorek.renshi.plugin.fragment.base

import com.damianchodorek.renshi.plugin.dialogfragment.FragmentPlugin
import com.damianchodorek.renshi.storeowner.BaseFragment

/**
 * Simple implementation of [FragmentPlugin] that has no controllers.
 * Fragment delegates its lifecycle methods to plugins methods,
 * so you can split your view logic into any number of plugins, so your code scales.
 * Your plugin should extend this class only to do basic initial operations like setting fragments
 * layout.
 * @author Damian Chodorek
 */
abstract class SimpleFragmentPlugin(
        protected val fragment: BaseFragment
) : com.pascalwelsch.compositeandroid.fragment.FragmentPlugin(), FragmentPlugin