package com.damianchodorek.renshi.plugin.dialogfragment.base

import com.damianchodorek.renshi.plugin.fragment.DialogFragmentPlugin
import com.damianchodorek.renshi.storeowner.BaseDialogFragment

abstract class SimpleDialogFragmentPlugin(
        protected val dialogFragment: BaseDialogFragment
) : com.pascalwelsch.compositeandroid.fragment.DialogFragmentPlugin(), DialogFragmentPlugin