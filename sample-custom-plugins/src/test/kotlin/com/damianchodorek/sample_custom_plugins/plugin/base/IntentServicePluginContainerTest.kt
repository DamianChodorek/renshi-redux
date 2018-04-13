package com.damianchodorek.sample_custom_plugins.plugin.base

import android.content.Intent
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import org.junit.Test

/**
 * @author Damian Chodorek
 */
class IntentServicePluginContainerTest {

    private val plugins = listOf<IntentServicePlugin>(mock(), mock())
    private val container = IntentServicePluginContainer().apply {
        plugins.forEach { plug(it) }
    }

    @Test
    fun init_doesNothingWithPlugins() {
        plugins.forEach { verifyZeroInteractions(it) }
    }

    @Test
    fun onCreate_delegatesToPlugins() {
        container.onCreate()
        plugins.forEach { verify(it).onCreate() }
    }

    @Test
    fun onDestroy_delegatesToPlugins() {
        container.onDestroy()
        plugins.forEach { verify(it).onDestroy() }
    }

    @Test
    fun onHandleIntent_delegatesToPlugins() {
        val intentMock = mock<Intent>()

        container.onHandleIntent(intentMock)

        plugins.forEach { verify(it).onHandleIntent(intentMock) }
    }
}