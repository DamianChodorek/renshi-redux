package com.damianchodorek.renshiredux.plugin

import android.view.View
import android.widget.Button
import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshiredux.controller.MakeApiCallControllerImpl
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pascalwelsch.compositeandroid.activity.ActivityDelegate
import kotlinx.android.synthetic.main.activity_main.*
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class MakeApiCallButtonPluginImplTest {

    private val button = mock<Button>()
    private val activityMock = mock<BaseActivity>().apply {
        whenever(makeApiCallBtn).thenReturn(button)
    }
    private val plugin = MakeApiCallButtonPluginImpl(activityMock)
    private val delegate = ActivityDelegate(activityMock).apply {
        addPlugin(plugin)
    }

    @Test
    fun createController_createsProperControllerInstance() {
        val controller = plugin.createController()
        assertThat(controller, instanceOf(MakeApiCallControllerImpl::class.java))
    }

    @Test
    fun makeApiCallClicks_setsButtonsOnClickListener() {
        delegate.onCreate(null)
        verify(button).setOnClickListener(any())
    }

    @Test
    fun makeApiCallClicks_doesNotEmitEventsWhenButtonNotClicked() {
        val testObserver = plugin.makeApiCallClicks.test()

        delegate.onCreate(null)

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

        delegate.onCreate(null)
        listener.onClick(button)

        testObserver
                .assertValueCount(1)
                .assertNotComplete()
                .assertNoErrors()
    }
}