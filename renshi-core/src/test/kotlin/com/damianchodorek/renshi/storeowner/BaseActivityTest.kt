package com.damianchodorek.renshi.storeowner

import com.damianchodorek.renshi.plugin.activity.ActivityPlugin
import com.damianchodorek.renshi.plugin.activity.base.BaseActivityPlugin
import com.damianchodorek.renshi.utils.impl.StoreTestImpl
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

/**
 * @author Damian Chodorek
 */
class BaseActivityTest {

    private val activity = spy(TestActivity())

    @Test
    fun addPlugin_castsPluginToActivityPluginAndAdds() {
        val pluginMock = mock<BaseActivityPlugin>() as ActivityPlugin

        activity.plug(pluginMock)

        verify(activity).addPlugin(pluginMock as com.pascalwelsch.compositeandroid.activity.ActivityPlugin)
    }

    @Test(expected = IllegalStateException::class)
    fun addPlugin_throwsIllegalStateException_whenPluginDoesntExtendBaseClass() {
        activity.plug(mock<ActivityPlugin>())
    }
}

private class TestActivity : BaseActivity() {

    override val store = StoreTestImpl()
}