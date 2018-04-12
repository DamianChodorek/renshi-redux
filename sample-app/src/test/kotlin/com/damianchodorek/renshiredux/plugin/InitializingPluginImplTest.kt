package com.damianchodorek.renshiredux.plugin

import com.damianchodorek.renshiredux.R
import com.damianchodorek.renshiredux.view.MainActivity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.pascalwelsch.compositeandroid.activity.ActivityDelegate
import org.junit.Test

class InitializingPluginImplTest {

    private val activityMock = mock<MainActivity>()
    private val plugin = InitializingPluginImpl(activityMock)

    /*
    NOTE that to test plugin you must add it do delegate as presented below.
    To test plugin method you call delegate.
     */
    private val delegate = ActivityDelegate(activityMock).apply {
        addPlugin(plugin)
    }

    @Test
    fun onCreate_setsProperContentView() {
        delegate.onCreate(null)
        verify(activityMock).setContentView(R.layout.activity_main)
    }
}