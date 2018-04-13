package com.damianchodorek.renshi.store.storeownercache.base

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache

/**
 * Provider of [ViewModelCache].
 * @author Damian Chodorek
 */
class ViewModelCacheProvider {

    /**
     * Creates new cache object or returns existing one.
     * @param context should be instance of AppCompatActivity.
     * @return created or existing inastance of [ViewModelCache].
     */
    fun provide(context: Context): StoreOwnerCache = ViewModelProviders
            .of(context as AppCompatActivity)
            .get(ViewModelCache::class.java)
}