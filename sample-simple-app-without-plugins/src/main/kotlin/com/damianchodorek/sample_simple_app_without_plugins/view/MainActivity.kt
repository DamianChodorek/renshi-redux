package com.damianchodorek.sample_simple_app_without_plugins.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.damianchodorek.sample_simple_app_without_plugins.R
import com.damianchodorek.sample_simple_app_without_plugins.store.MainActivityStore

/**
 * This simple example shows how to use pure redux to communicate two fragments with each other.
 *
 * This is NOT recommended way of using Renshi because you may end up with a lot of code in your
 * activities or fragments that won't scale.
 */
class MainActivity : AppCompatActivity() {

    val store = MainActivityStore()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
