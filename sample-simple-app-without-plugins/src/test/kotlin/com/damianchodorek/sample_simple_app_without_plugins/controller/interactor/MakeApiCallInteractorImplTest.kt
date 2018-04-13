package com.damianchodorek.sample_simple_app_without_plugins.controller.interactor

import com.damianchodorek.sample_simple_app_without_plugins.utils.RxTestRule
import io.reactivex.schedulers.TestScheduler
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * @author Damian Chodorek
 */
class MakeApiCallInteractorImplTest {

    @Suppress("unused")
    @get:Rule
    val rxRule = RxTestRule()
    private val testScheduler = TestScheduler()
    private val interactor = MakeApiCallInteractorImpl(testScheduler)
    private val callDuration = 4L

    @Test
    fun makeApiCall_waitsProperTimeAndCompletes() {
        val testObserver = interactor.makeFakeApiCall().test()

        testScheduler.advanceTimeBy(callDuration, TimeUnit.SECONDS)

        testObserver.assertComplete()
    }

    @Test
    fun makeApiCall_doesNotComplete_whenProperTimeNotPassed() {
        val testObserver = interactor.makeFakeApiCall().test()

        testScheduler.advanceTimeBy(callDuration - 1, TimeUnit.SECONDS)

        testObserver
                .assertNotComplete()
                .assertNoErrors()
    }
}