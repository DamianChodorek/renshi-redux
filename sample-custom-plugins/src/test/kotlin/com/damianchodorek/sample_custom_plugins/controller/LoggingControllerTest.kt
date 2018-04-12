package com.damianchodorek.sample_custom_plugins.controller

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class LoggingControllerTest {

    private val loggerMock = mock<(String) -> Unit>()
    private val controller = LoggingController(loggerMock)

    @Test
    fun onAttachPlugin_logsProperLog() {
        controller.onAttachPlugin()
        verify(loggerMock).invoke("onAttachPlugin")
    }

    @Test
    fun onDetachPlugin_logsProperLog() {
        controller.onDetachPlugin()
        verify(loggerMock).invoke("onDetachPlugin")
    }

    @Test
    fun onDestroy_logsProperLog() {
        controller.onDestroy()
        verify(loggerMock).invoke("onDestroy")
    }
}