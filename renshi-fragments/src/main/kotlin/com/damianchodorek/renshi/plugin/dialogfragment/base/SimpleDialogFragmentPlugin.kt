package com.damianchodorek.renshi.plugin.dialogfragment.base

import com.damianchodorek.renshi.plugin.fragment.DialogFragmentPlugin
import com.damianchodorek.renshi.storeowner.BaseDialogFragment

/**
 * Simple implementation of [DialogFragmentPlugin] that has no controllers.
 * DialogFragment delegates its lifecycle methods to plugins methods,
 * so you can split your view logic into any number of plugins, so your code scales.
 * Your plugin should extend this class only to do basic initial operations like setting
 * dialog fragment layout.
 * @author Damian Chodorek
 */
abstract class SimpleDialogFragmentPlugin(
        protected val dialogFragment: BaseDialogFragment
) : com.pascalwelsch.compositeandroid.fragment.DialogFragmentPlugin(), DialogFragmentPlugin