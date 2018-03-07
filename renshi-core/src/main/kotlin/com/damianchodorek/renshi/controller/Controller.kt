package com.damianchodorek.renshi.controller

import com.damianchodorek.renshi.store.Store
import io.reactivex.disposables.Disposable

@Suppress("RedundantUnitReturnType")
interface Controller {

    fun onAttachPlugin(): Unit
    fun onDetachPlugin(): Unit
    fun onDestroy(): Unit
    fun setStoreRef(store: Store<*>): Unit
    fun setPluginRef(plugin: Any): Unit
    fun disposeOnDetach(disposableToAdd: Disposable): Unit
    fun disposeOnDestroy(disposableToAdd: Disposable): Unit
}