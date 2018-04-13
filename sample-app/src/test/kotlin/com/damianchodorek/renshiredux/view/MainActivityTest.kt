package com.damianchodorek.renshiredux.view

import com.damianchodorek.renshiredux.store.MainActivityStore
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * @author Damian Chodorek
 */
class MainActivityTest {

    private val activity = MainActivity()

    @Test
    fun store_returnsProperStoreInstance() {
        assertThat(activity.store, instanceOf(MainActivityStore::class.java))
    }
}