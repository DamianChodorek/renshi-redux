package com.damianchodorek.renshiredux.view

import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshiredux.plugin.InitializingPluginImpl
import com.damianchodorek.renshiredux.store.MainActivityStore

/**
 * This example shows how to use Renshi framework. Every activity/fragment should extend framework
 * base classes for ease of use. It enables you to split your activity/fragment into many plugins
 * that handle view stuff. Each plugin can have any number of controllers that handle business logic.
 * Everything is synchronized by one reference to activity store.
 *
 * This is recommended way of using Renshi. It enables you to create scalable code without God Objects.
 */
class MainActivity : BaseActivity() {

    /**
     * Store object that all of activity subviews will share.
     */
    override val store = MainActivityStore()

    init {
        // Plugins must be added in constructor.
        plug(InitializingPluginImpl(this))
    }
}
