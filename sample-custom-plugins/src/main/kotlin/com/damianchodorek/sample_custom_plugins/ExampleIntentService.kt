/**
 * Created by mateuszkoslacz on 14.12.2016.
 */
package com.damianchodorek.sample_custom_plugins

import android.app.IntentService
import android.content.Intent
import com.damianchodorek.renshi.plugin.Plugable
import com.damianchodorek.renshi.store.StoreOwner
import com.damianchodorek.renshi.store.storeownercache.base.BaseStoreOwnerCache
import com.damianchodorek.sample_custom_plugins.plugin.LoggingPlugin
import com.damianchodorek.sample_custom_plugins.plugin.PluginWithLoggingController
import com.damianchodorek.sample_custom_plugins.plugin.base.IntentServicePlugin
import com.damianchodorek.sample_custom_plugins.plugin.base.IntentServicePluginContainer
import com.damianchodorek.sample_custom_plugins.store.ExampleIntentServiceStore

/**
 * This example show how to make your own plugins and plug them into any class that you need.
 * If for some reason you can't extend Renshi base store owners
 * (for example: [com.damianchodorek.renshi.storeowner.BaseActivity]),
 * you can create your own store owner and delegate it's methods to plugins as shown below.
 * @author Damian Chodorek
 */
class ExampleIntentService
    : IntentService("ExampleIntentService"), Plugable<IntentServicePlugin>, StoreOwner {

    override val store = ExampleIntentServiceStore()
    val cache = BaseStoreOwnerCache()

    /**
     * Container for plugins that you use to delegate any method from this service to its plugins.
     */
    private val pluginContainer = IntentServicePluginContainer()

    init {
        plug(LoggingPlugin(this))
        plug(PluginWithLoggingController(this))
    }

    override fun plug(plugin: IntentServicePlugin) = pluginContainer.plug(plugin)

    override fun onCreate() {
        super.onCreate()
        pluginContainer.onCreate()
    }

    override fun onDestroy() {
        pluginContainer.onDestroy()
        // it's very important to clear cache before store owner is destroyed
        // it enables controllers to be properly destroyed
        cache.onDestroy()
        super.onDestroy()
    }

    override fun onHandleIntent(intent: Intent?) = pluginContainer.onHandleIntent(intent)
}