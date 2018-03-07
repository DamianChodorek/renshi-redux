package com.damianchodorek.renshi.plugin.fragment.base

import com.damianchodorek.renshi.plugin.dialogfragment.FragmentPlugin
import com.damianchodorek.renshi.storeowner.BaseFragment

abstract class SimpleFragmentPlugin(
        protected val fragment: BaseFragment
) : com.pascalwelsch.compositeandroid.fragment.FragmentPlugin(), FragmentPlugin