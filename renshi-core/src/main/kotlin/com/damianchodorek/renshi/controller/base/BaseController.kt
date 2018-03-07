package com.damianchodorek.renshi.controller.base

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.store.state.State
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

@Suppress("UNCHECKED_CAST", "RedundantUnitReturnType")
abstract class BaseController<VIEW, STATE : State> : Controller {

    private val detachDisposable = CompositeDisposable()
    private val destroyDisposable = CompositeDisposable()

    protected lateinit var store: Store<STATE>

    private var pluginWeakRef: WeakReference<VIEW>? = null
    protected var plugin: VIEW? = null
        get() = pluginWeakRef?.get()

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
        this.pluginWeakRef = WeakReference(plugin as VIEW)
    }
}