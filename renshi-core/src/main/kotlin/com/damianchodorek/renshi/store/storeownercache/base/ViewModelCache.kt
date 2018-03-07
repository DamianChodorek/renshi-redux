package com.damianchodorek.renshi.store.storeownercache.base

import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache

internal class ViewModelCache(
        internal val baseStoreOwnerCache: BaseStoreOwnerCache = BaseStoreOwnerCache()
) : ViewModel(), StoreOwnerCache by baseStoreOwnerCache {

    override fun onCleared() {
        onDestroy()
        super.onCleared()
    }

    @VisibleForTesting
    internal fun onClearedInternal() {
        onCleared()
    }
}