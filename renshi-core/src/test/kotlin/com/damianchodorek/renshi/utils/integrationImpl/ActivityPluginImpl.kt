package com.damianchodorek.renshi.utils.integrationImpl

import android.content.Context
import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.plugin.activity.base.BaseActivityPlugin
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache
import com.damianchodorek.renshi.storeowner.BaseActivity

/**
 * @author Damian Chodorek
 */
internal class ActivityPluginImpl(
        activity: BaseActivity,
        storeOwnerCacheProvider: (Context) -> StoreOwnerCache,
        private val controllerProvider: () -> Controller?
) : BaseActivityPlugin(
        activity = activity,
        storeOwnerCacheProvider = storeOwnerCacheProvider
) {

    var testString = ""

    override fun createController() = controllerProvider()

    fun getClassName(): String = this::class.java.name
}