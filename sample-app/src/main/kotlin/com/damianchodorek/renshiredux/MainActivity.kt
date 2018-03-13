package com.damianchodorek.renshiredux

import android.os.Bundle
import com.damianchodorek.renshi.store.Store
import com.damianchodorek.renshi.storeowner.BaseActivity

class MainActivity : BaseActivity() {

    override val store: Store<*>
        get() = throw NotImplementedError()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
