package com.damianchodorek.renshi.plugin.activity.base

import com.damianchodorek.renshi.plugin.activity.ActivityPlugin
import com.damianchodorek.renshi.storeowner.BaseActivity

abstract class SimpleActivityPlugin(
        protected val activity: BaseActivity
) : com.pascalwelsch.compositeandroid.activity.ActivityPlugin(), ActivityPlugin