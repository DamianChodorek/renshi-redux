package com.damianchodorek.renshi.plugin.activity.base

import android.content.Context
import com.damianchodorek.renshi.controller.ControllerOwner
import com.damianchodorek.renshi.plugin.PluginDelegate
import com.damianchodorek.renshi.plugin.base.PluginDelegateBuilder
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache
import com.damianchodorek.renshi.store.storeownercache.base.ViewModelCacheProvider
import com.damianchodorek.renshi.storeowner.BaseActivity

/**
 * Base class for all activity plugins. Activity delegates its lifecycle methods to plugins methods,
 * so you can split your view logic into any number of plugins, so your code scales.
 * Plugin delegates all logic to controllers ([com.damianchodorek.renshi.controller.Controller]).
 * Creates controller instance (or many controllers by using [com.damianchodorek.renshi.controller.CompositeController]).
 * Objects of this class are passive - they don't call controllers directly. Instead they
 * may emit events by using RxJava subjects (for example: [io.reactivex.subjects.PublishSubject]).
 * Controllers have reference to plugin so they can observe emitted events.
 *
 * @param activity activity instance.
 * @property pluginDelegateBuilder builder of [PluginDelegate].
 * @property storeOwnerCacheProvider function that returns [com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache].
 * By default it is function that returns [com.damianchodorek.renshi.store.storeownercache.base.ViewModelCacheProvider].
 */
@Suppress("RedundantUnitReturnType")
abstract class BaseActivityPlugin(
        activity: BaseActivity,
        private val pluginDelegateBuilder: PluginDelegateBuilder = PluginDelegateBuilder(),
        private val storeOwnerCacheProvider: (Context) -> StoreOwnerCache =
                { context -> ViewModelCacheProvider().provide(context) }
) : SimpleActivityPlugin(activity), ControllerOwner {

    private val pluginDelegate: PluginDelegate by lazy {
        pluginDelegateBuilder.build(
                storeProvider = { activity.store },
                storeOwnerCacheProvider = { storeOwnerCacheProvider(activity) },
                controllerProvider = { createController() },
                pluginProvider = { this }
        )
    }

    override fun onStart(): Unit {
        super.onStart()
        pluginDelegate.onStartPlugin()
    }

    override fun onStop(): Unit {
        pluginDelegate.onStopPlugin()
        super.onStop()
    }
}