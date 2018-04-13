package com.damianchodorek.renshi.store.storeownercache.base

import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import com.damianchodorek.renshi.store.storeownercache.StoreOwnerCache

/**
 * Implementation of [StoreOwnerCache] that uses [android.arch.lifecycle.ViewModel]
 * from Android Arch Components. It survives orientation chages.
 * @author Damian Chodorek
 */
internal class ViewModelCache(
        internal val baseStoreOwnerCache: BaseStoreOwnerCache = BaseStoreOwnerCache()
) : ViewModel(), StoreOwnerCache by baseStoreOwnerCache {

    /**
     * Calls [StoreOwnerCache.onDestroy] to clear cache.
     */
    override fun onCleared() {
        onDestroy()
        super.onCleared()
    }

    @VisibleForTesting
    internal fun onClearedInternal() {
        onCleared()
    }
}