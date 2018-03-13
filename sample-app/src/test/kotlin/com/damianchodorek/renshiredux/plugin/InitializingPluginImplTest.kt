package com.damianchodorek.renshiredux.plugin

import com.damianchodorek.renshiredux.MainActivity
import com.damianchodorek.renshiredux.R
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.pascalwelsch.compositeandroid.activity.ActivityDelegate
import org.junit.Test

class InitializingPluginImplTest {

    private val activityMock = mock<MainActivity>()
    private val plugin = InitializingPluginImpl(activityMock)
    private val delegate = ActivityDelegate(activityMock).apply {
        addPlugin(plugin)
    }

    @Test
    fun onCreate_setsProperContentView() {
        delegate.onCreate(null)
        verify(activityMock).setContentView(R.layout.activity_main)
    }
}