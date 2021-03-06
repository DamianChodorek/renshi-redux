package com.damianchodorek.renshi.plugin.fragment.base

import com.damianchodorek.renshi.controller.ControllerOwner
import com.damianchodorek.renshi.plugin.PluginDelegate
import com.damianchodorek.renshi.plugin.base.PluginDelegateBuilder
import com.damianchodorek.renshi.store.storeownercache.base.ViewModelCacheProvider
import com.damianchodorek.renshi.storeowner.BaseFragment

/**
 * Base class for all fragment plugins. Fragment delegates its lifecycle methods to plugins methods,
 * so you can split your view logic into any number of plugins, so your code scales.
 * Plugin delegates all logic to controllers ([com.damianchodorek.renshi.controller.Controller]).
 * Creates controller instance (or many controllers by using [com.damianchodorek.renshi.controller.CompositeController]).
 * Objects of this class are passive - they don't call controllers directly. Instead they
 * may emit events by using RxJava subjects (for example: [io.reactivex.subjects.PublishSubject]).
 * Controllers have reference to plugin so they can observe emitted events.
 *
 * @param fragment DialogFragment instance.
 * @property pluginDelegateBuilder builder of [PluginDelegate].
 * @author Damian Chodorek
 */
@Suppress("RedundantUnitReturnType")
abstract class BaseFragmentPlugin(
        fragment: BaseFragment,
        private var pluginDelegateBuilder: PluginDelegateBuilder = PluginDelegateBuilder()
) : SimpleFragmentPlugin(fragment), ControllerOwner {

    private var storeOwnerCacheProvider = ViewModelCacheProvider()
    private val pluginDelegate: PluginDelegate by lazy {
        pluginDelegateBuilder.build(
                storeProvider = { fragment.store },
                storeOwnerCacheProvider = { storeOwnerCacheProvider.provide(fragment.activity!!) },
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
