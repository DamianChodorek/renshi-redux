package com.damianchodorek.renshiredux.plugin

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import com.damianchodorek.renshi.storeowner.BaseFragment
import com.damianchodorek.renshiredux.R
import com.damianchodorek.renshiredux.controller.MakeApiCallControllerImpl
import com.damianchodorek.renshiredux.presenter.MakeApiCallBtnPresenterImpl
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pascalwelsch.compositeandroid.fragment.FragmentDelegate
import kotlinx.android.synthetic.main.fragment_make_api_call.view.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class MakeApiCallBtnFragmentPluginImplTest {

    private val viewGroupMock = mock<ViewGroup>()
    private val button = mock<Button>()
    private val fragmentViewMock = mock<View>().apply {
        whenever(this.makeApiCallBtn).thenReturn(button)
    }
    private val inflaterMock = mock<LayoutInflater>().apply {
        whenever(inflate(any<Int>(), any(), any())).thenReturn(fragmentViewMock)
    }
    private val fragmentMock = mock<BaseFragment>()
    private val plugin = MakeApiCallBtnFragmentPluginImpl(fragmentMock)
    private val delegate = FragmentDelegate(fragmentMock).apply {
        addPlugin(plugin)
    }

    @Test
    fun createController_createsProperCompositeController() {
        val controller = plugin.createController()
        assertThat(controller.controllers.size, equalTo(2))
    }

    @Test
    fun createController_createsCompositeControllerWithMakeApiCallController() {
        val controller = plugin.createController()
        assertThat(controller.controllers[0], instanceOf(MakeApiCallControllerImpl::class.java))
    }

    @Test
    fun createController_createsCompositeControllerWithMakeApiCallBtnPresenterImpl() {
        val controller = plugin.createController()
        assertThat(controller.controllers[1], instanceOf(MakeApiCallBtnPresenterImpl::class.java))
    }

    @Test
    fun onCreateView_inflatesProperLayout() {
        onCreateView()
        inflaterMock.inflate(R.layout.fragment_make_api_call, viewGroupMock, false)
    }

    private fun onCreateView() = delegate.onCreateView(inflaterMock, viewGroupMock, null)

    @Test
    fun onCreateView_returnsProperView() {
        val view = onCreateView()
        assertThat(view, equalTo(fragmentViewMock))
    }

    @Test
    fun makeApiCallClicks_doesNotEmitEventsWhenButtonNotClicked() {
        val testObserver = plugin.makeApiCallClicks.test()

        onCreateView()

        testObserver
                .assertNoValues()
                .assertNoErrors()
                .assertNotComplete()
    }

    @Test
    fun makeApiCallClicks_emitsEventsWhenButtonClicked() {
        lateinit var listener: View.OnClickListener
        whenever(button.setOnClickListener(any())).thenAnswer {
            listener = it.arguments[0] as View.OnClickListener
            it.arguments[0]
        }
        val testObserver = plugin.makeApiCallClicks.test()

        onCreateView()
        listener.onClick(button)

        testObserver
                .assertValueCount(1)
                .assertNotComplete()
                .assertNoErrors()
    }

    @Test
    fun hideButton_setsButtonGone() {
        whenever(fragmentMock.view).thenReturn(button)

        plugin.hideButton()

        verify(button).visibility = GONE
    }

    @Test
    fun showButton_setsButtonVisible() {
        whenever(fragmentMock.view).thenReturn(button)

        plugin.showButton()

        verify(button).visibility = VISIBLE
    }
}