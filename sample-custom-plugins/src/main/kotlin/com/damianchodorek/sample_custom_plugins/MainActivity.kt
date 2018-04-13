package com.damianchodorek.sample_custom_plugins

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // everything important is in ExampleIntentService
        // in this example nothing is shown on screen, I used logs to present app behaviour
        startService(Intent(this, ExampleIntentService::class.java))
    }
}
