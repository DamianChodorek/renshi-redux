package com.damianchodorek.renshiredux.plugin

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import com.damianchodorek.renshi.storeowner.BaseFragment
import com.damianchodorek.renshiredux.R
import com.damianchodorek.renshiredux.presenter.ProgressBarPresenterImpl
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pascalwelsch.compositeandroid.fragment.FragmentDelegate
import kotlinx.android.synthetic.main.fragment_progress_bar.view.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ProgressBarFragmentPluginImplTest {

    private val viewGroupMock = mock<ViewGroup>()
    private val progressMock = mock<ProgressBar>()
    private val fragmentMock = mock<BaseFragment>()
    private val fragmentViewMock = mock<View>().apply {
        whenever(apiCallProgressBar).thenReturn(progressMock)
    }
    private val inflaterMock = mock<LayoutInflater>().apply {
        whenever(inflate(any<Int>(), any(), any())).thenReturn(fragmentViewMock)
    }
    private val plugin = ProgressBarFragmentPluginImpl(fragmentMock)

    /*
    NOTE that to test plugin you must add it do delegate as presented below.
    To test plugin method you call delegate.
     */
    private val delegate = FragmentDelegate(fragmentMock).apply {
        addPlugin(plugin)
    }

    @Test
    fun createController_createsCompositeControllerWithMakeApiCallController() {
        val controller = plugin.createController()
        assertThat(controller, instanceOf(ProgressBarPresenterImpl::class.java))
    }

    @Test
    fun onCreateView_inflatesProperLayout() {
        onCreateView()
        inflaterMock.inflate(R.layout.fragment_progress_bar, viewGroupMock, false)
    }

    private fun onCreateView() = delegate.onCreateView(inflaterMock, viewGroupMock, null)

    @Test
    fun onCreateView_returnsProperView() {
        val view = onCreateView()
        assertThat(view, equalTo(fragmentViewMock))
    }

    @Test
    fun hideLoading_setsProgressGone() {
        whenever(fragmentMock.view).thenReturn(progressMock)

        plugin.hideLoading()

        verify(progressMock).visibility = GONE
    }

    @Test
    fun showLoading_setsProgressVisible() {
        whenever(fragmentMock.view).thenReturn(progressMock)

        plugin.showLoading()

        verify(progressMock).visibility = VISIBLE
    }
}