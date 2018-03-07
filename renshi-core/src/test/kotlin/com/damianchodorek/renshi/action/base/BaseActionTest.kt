package com.damianchodorek.renshi.action.base

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNull
import org.junit.Test

class BaseActionTest {

    @Test
    fun renderMark_returnsNullAsDefault() {
        val action = object : BaseAction() {}
        assertNull(action.renderMark)
    }

    @Suppress("UnnecessaryVariable")
    @Test
    fun renderMark_getsOverridenValue() {
        val mark = "random object"

        val action = object : BaseAction() {
            override val renderMark = mark
        }

        assertThat(action.renderMark, equalTo(mark))
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