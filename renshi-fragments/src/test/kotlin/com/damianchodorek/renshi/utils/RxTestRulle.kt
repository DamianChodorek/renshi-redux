package com.damianchodorek.renshi.utils

import com.damianchodorek.renshi.store.dispatcher.Dispatcher
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * @author Damian Chodorek
 */
class RxTestRule : TestRule {

    override fun apply(base: Statement?, description: Description?) =
            object : Statement() {

                override fun evaluate() {
                    RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                    RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                    RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }
                    Dispatcher.schedulerProvider = { Schedulers.trampoline() }

                    try {
                        base!!.evaluate()
                    } finally {
                        RxJavaPlugins.reset()
                        Dispatcher.resetSchedulerProvider()
                    }
                }
            }
}