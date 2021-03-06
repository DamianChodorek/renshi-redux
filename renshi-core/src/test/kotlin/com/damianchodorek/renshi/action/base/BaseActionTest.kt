package com.damianchodorek.renshi.action.base

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * @author Damian Chodorek
 */
class BaseActionTest {

    @Test
    fun renderMark_returnsNullAsDefault() {
        val action = object : BaseAction() {}
        assertNull(action.actionMark)
    }

    @Suppress("UnnecessaryVariable")
    @Test
    fun renderMark_getsOverridenValue() {
        val mark = "random object"

        val action = object : BaseAction() {
            override val actionMark = mark
        }

        assertThat(action.actionMark, equalTo(mark))
    }

    @Test
    fun singleTime_returnsFalseAsDefault() {
        val action = object : BaseAction() {}
        assertThat(action.singleTime, equalTo(false))
    }

    @Test
    fun singleTime_getsOverridenValue() {
        val action = object : BaseAction() {
            override val singleTime = true
        }

        assertThat(action.singleTime, equalTo(true))
    }
}