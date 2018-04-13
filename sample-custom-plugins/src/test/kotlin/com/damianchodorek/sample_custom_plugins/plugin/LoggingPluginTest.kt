package com.damianchodorek.sample_custom_plugins.plugin

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

/**
 * @author Damian Chodorek
 */
class LoggingPluginTest {

    private val logMock = mock<(String) -> Unit>()
    private val plugin = LoggingPlugin(mock(), logMock)

    @Test
    fun onCreate_logsCallingFunctionName() {
        plugin.onCreate()
        verify(logMock).invoke("onCreate")
    }

    @Test
    fun onDestroy_logsCallingFunctionName() {
        plugin.onDestroy()
        verify(logMock).invoke("onDestroy")
    }

    @Test
    fun onHandleIntent_logsCallingFunctionName() {
        plugin.onHandleIntent(mock())
        verify(logMock).invoke("onHandleIntent")
    }
}