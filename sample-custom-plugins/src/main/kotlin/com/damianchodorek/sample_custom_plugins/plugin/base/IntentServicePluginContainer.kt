package com.damianchodorek.sample_custom_plugins.plugin.base

import android.content.Intent
import com.damianchodorek.renshi.plugin.base.PluginContainer

/**
 * Container for plugins that composes them into one plugin.
 * @author Damian Chodorek
 */
class IntentServicePluginContainer : PluginContainer<IntentServicePlugin>(), IntentServicePlugin {

    override fun onCreate() = plugins.forEach { it.onCreate() }

    override fun onDestroy() = plugins.forEach { it.onDestroy() }

    override fun onHandleIntent(intent: Intent?) = plugins.forEach { it.onHandleIntent(intent) }
}