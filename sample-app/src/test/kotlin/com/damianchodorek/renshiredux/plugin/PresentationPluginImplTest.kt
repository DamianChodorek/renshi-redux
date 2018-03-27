package com.damianchodorek.renshiredux.plugin

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshiredux.presenter.MainPresenterImpl
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import kotlinx.android.synthetic.main.activity_main.*
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class PresentationPluginImplTest {

    private val activityMock = mock<BaseActivity>()
    private val plugin = PresentationPluginImpl(activityMock)

    @Test
    fun createController_createsProperControllerInstance() {
        val controller = plugin.createController()
        assertThat(controller, instanceOf(MainPresenterImpl::class.java))
    }

    @Test
    fun hideButton_setsButtonGone() {
        val buttonMock = mock<Button>()
        whenever(activityMock.makeApiCallBtn).thenReturn(buttonMock)

        plugin.hideButton()

        verify(buttonMock).visibility = GONE
    }

    @Test
    fun showButton_setsButtonVisible() {
        val buttonMock = mock<Button>()
        whenever(activityMock.makeApiCallBtn).thenReturn(buttonMock)

        plugin.showButton()

        verify(buttonMock).visibility = VISIBLE
    }

    @Test
    fun hideLoading_setsButtonGone() {
        val progressMock = mock<ProgressBar>()
        whenever(activityMock.apiCallProgressBar).thenReturn(progressMock)

        plugin.hideLoading()

        verify(progressMock).visibility = GONE
    }

    @Test
    fun showLoading_setsButtonVisible() {
        val progressMock = mock<ProgressBar>()
        whenever(activityMock.apiCallProgressBar).thenReturn(progressMock)

        plugin.showLoading()

        verify(progressMock).visibility = VISIBLE
    }
}