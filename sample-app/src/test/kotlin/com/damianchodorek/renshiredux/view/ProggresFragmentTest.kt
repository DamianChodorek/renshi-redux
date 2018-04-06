package com.damianchodorek.renshiredux.view

import com.damianchodorek.renshi.storeowner.BaseActivity
import com.damianchodorek.renshiredux.store.MainActivityStore
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ProggresFragmentTest {

    private val storeMock = mock<MainActivityStore>()
    private val fragment = mock<ProggresFragment>().apply {
        whenever(store).thenCallRealMethod()

        val activityMock = mock<BaseActivity>()
        whenever(activityMock.store).thenReturn(storeMock)

        whenever(activity).thenReturn(activityMock)
    }

    @Test
    fun store_returnsStoreFromActivity() {
        assertThat(fragment.store as MainActivityStore, equalTo(storeMock))
    }
}