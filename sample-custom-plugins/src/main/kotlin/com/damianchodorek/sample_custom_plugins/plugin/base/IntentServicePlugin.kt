package com.damianchodorek.sample_custom_plugins.plugin.base

import android.content.Intent

interface IntentServicePlugin {

    fun onCreate()
    fun onDestroy()
    fun onHandleIntent(intent: Intent?)
}