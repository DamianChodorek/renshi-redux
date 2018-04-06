package com.damianchodorek.renshiredux

import com.damianchodorek.renshiredux.store.MainActivityStore
import com.damianchodorek.renshiredux.view.MainActivity
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class MainActivityTest {

    private val activity = MainActivity()

    @Test
    fun store_returnsProperStoreInstance() {
        assertThat(activity.store, instanceOf(MainActivityStore::class.java))
    }
}