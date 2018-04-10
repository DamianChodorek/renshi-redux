package com.damianchodorek.renshi.controller.base

import android.support.annotation.CallSuper
import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.state.State
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * Base implementation of [Controller] to reduce boilerplate. Your controllers should extend
 * this class.
 * @param PLUGIN the type of a plugin that controller will access.
 * @param STATE the subtype of [com.damianchodorek.renshi.store.state.State] that will be
 * used by constrollers store reference.
 */
@Suppress("UNCHECKED_CAST", "RedundantUnitReturnType")
abstract class BaseController<PLUGIN, STATE : State> : Controller {

    private val detachDisposable = CompositeDisposable()
    private val destroyDisposable = CompositeDisposable()

    protected lateinit var store: Store<STATE>

    private var pluginWeakRef: WeakReference<PLUGIN>? = null
    protected var plugin: PLUGIN? = null
        get() = pluginWeakRef?.get()

    @CallSuper
    override fun onDetachPlugin(): Unit {
        detachDisposable.clear()
        pluginWeakRef = null
    }

    override fun onDestroy(): Unit {
        destroyDisposable.clear()
    }

    override fun disposeOnDetach(disposableToAdd: Disposable): Unit {
        detachDisposable.add(disposableToAdd)
    }

    override fun disposeOnDestroy(disposableToAdd: Disposable): Unit {
        destroyDisposable.add(disposableToAdd)
    }

    override fun setStoreRef(store: Store<*>): Unit {
        this.store = store as Store<STATE>
    }

    override fun setPluginRef(plugin: Any): Unit {
        this.pluginWeakRef = WeakReference(plugin as PLUGIN)
    }
}