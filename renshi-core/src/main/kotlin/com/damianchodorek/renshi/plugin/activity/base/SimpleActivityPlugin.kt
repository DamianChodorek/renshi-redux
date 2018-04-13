package com.damianchodorek.renshi.plugin.activity.base

import com.damianchodorek.renshi.plugin.activity.ActivityPlugin
import com.damianchodorek.renshi.storeowner.BaseActivity

/**
 * Simple implementation of [ActivityPlugin] that has no controllers.
 * Activity delegates its lifecycle methods to plugins methods,
 * so you can split your view logic into any number of plugins, so your code scales.
 * Your plugin should extend this class only to do basic initial operations like setting activity
 * layout.
 * @author Damian Chodorek
 */
abstract class SimpleActivityPlugin(
        protected val activity: BaseActivity
) : com.pascalwelsch.compositeandroid.activity.ActivityPlugin(), ActivityPlugin