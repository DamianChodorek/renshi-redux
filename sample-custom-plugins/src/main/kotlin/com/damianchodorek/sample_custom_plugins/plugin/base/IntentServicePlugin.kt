package com.damianchodorek.sample_custom_plugins.plugin.base

import android.content.Intent

/**
 * Interface for all service plugins with delegate methods.
 * @author Damian Chodorek
 */
interface IntentServicePlugin {

    fun onCreate()
    fun onDestroy()
    fun onHandleIntent(intent: Intent?)
}