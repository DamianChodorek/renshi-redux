package com.damianchodorek.renshi.store.storeownercache.base

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache

class ViewModelCacheProvider {

    fun provide(context: Context): StoreOwnerCache = ViewModelProviders
            .of(context as AppCompatActivity)
            .get(ViewModelCache::class.java)
}