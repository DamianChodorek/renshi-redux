package com.damianchodorek.renshi.utils.integrationImpl

import com.damianchodorek.renshi.action.base.BaseAction

internal class ComplexAction(
        override val singleTime: Boolean = false,
        override val actionMark: String = "test action mark"
) : BaseAction()