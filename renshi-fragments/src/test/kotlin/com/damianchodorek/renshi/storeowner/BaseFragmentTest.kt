package com.damianchodorek.renshi.storeowner

import com.damianchodorek.renshi.plugin.dialogfragment.FragmentPlugin
import com.damianchodorek.renshi.plugin.fragment.base.BaseFragmentPlugin
import com.damianchodorek.renshi.utils.impl.StoreTestImpl
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class BaseFragmentTest {

    private val fragment = spy(TestFragment())

    @Test
    fun addPlugin_castsPluginToFragmentPluginAndAdds() {
        val pluginMock = mock<BaseFragmentPlugin>() as FragmentPlugin

        fragment.plug(pluginMock)

        verify(fragment).addPlugin(pluginMock as com.pascalwelsch.compositeandroid.fragment.FragmentPlugin)
    }

    @Test(expected = IllegalStateException::class)
    fun addPlugin_throwsIllegalStateException_whenPluginDoesntExtendBaseClass() {
        fragment.plug(mock<FragmentPlugin>())
    }

}

private class TestFragment : BaseFragment() {

    override val store = StoreTestImpl()
}