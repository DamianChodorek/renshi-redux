package com.damianchodorek.renshiredux

import com.damianchodorek.renshi.controller.Controller
import com.damianchodorek.renshi.plugin.activity.ActivityPlugin
import com.damianchodorek.renshi.plugin.dialogfragment.FragmentPlugin
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Top level contract that describes architectural components of [com.damianchodorek.renshiredux.view.MainActivity]
 * and its fragments. For separate and reusable views or activities you should probably define separate contracts.
 */
@Suppress("unused")
interface Contract {

    /**
     * Plugins are delegates of view (activity, fragment) logic.
     */
    interface Plugin {
        interface InitializingPlugin : ActivityPlugin

        interface MakeApiCallBtnFragmentPlugin : FragmentPlugin {
            val makeApiCallClicks: Observable<Unit>
            fun hideButton()
            fun showButton()
        }

        interface ProgressBarFragmentPlugin : FragmentPlugin {
            fun hideLoading()
            fun showLoading()
        }
    }

    /**
     * Controllers handle events from plugins and store and perform logic.
     * Plugins are passive so controllers don't have any public fields or methods.
     * We only mark them with interface to have ability to inject them for example with Dagger
     * by using layer of abstraction.
     */
    interface PluginController {
        interface MakeApiCallBtnController : Controller
    }

    /**
     * Presenters are also controllers but their responsibility is to translate new state to plugin calls.
     */
    interface PluginPresenter {
        interface MakeApiCallBtnPresenter : Controller
        interface ProgressBarPresenter : Controller
    }

    /**
     * Interactor is layer of abstraction between controller and data repository.
     */
    interface Interactor {
        interface MakeApiCallInteractor {
            fun makeFakeApiCall(): Completable
        }
    }
}