# Renshi
### Highly composable [redux](https://redux.js.org/) library for Android.
## Why Renshi?

- Redux is predictable state container. It helps you write applications that behave consistently in multithreaded environment and are easy to test.
- Plugin mechanism helps you easily scale your code, by following open/closed principle. Delegate all code from Activities/Fragments/Services/Views to as many plugins as you wish.
- Every plugin can be further extended by attaching as many controllers/presenters as you wish.
- Renshi is easily extensible. It enables you to write your own plugins and redux components.
- You can pick any part of the library that is interesting to you and forget about the rest.
- Renshi is written in TDD, 100% Kotlin, based on RxJava and great library called [CompositeAndroid](https://github.com/passsy/CompositeAndroid).

To learn more about advantages of redux visit [official redux website](https://redux.js.org/).

## Project modules

- `renshi-core` - contains basic interfaces, redux components, plugins and base class for `Activity`.
- `renshi-fragments` - contains plugins and base classes for `Fragment` and `DialogFragment`.
- `sample-app` - example app that shows how to use Renshi in most efficient and recommended way (along with tests). It shows how to use redux to communicate two fragments with each other.
- `sample-simple-app-without-plugins` - simple example that shows how to use pure redux to communicate two fragments with each other. This is NOT recommended way of using Renshi because without plugins you may end up with a lot of code in your activities or fragments that won't scale.
- `sample-custom-plugins` - this example shows how to make your own plugins and plug them into any class that you need. If for some reason you can't extend Renshi base store owners (for example: `BaseActivity`), you can easily create your own store owner and delegate it's methods to plugins.

## How it works?

Below description shows full capabilities of Renshi, but you don't have to use it that way. You can only use redux components and forget about plugins or controllers. Let's begin.

Simplified data flow looks like this:

- `Activity` or `Fragment` calls plugins.
- Plugins emit events to controllers.
- Controllers dispatch actions to shared store.
- Store calls reducers to create new state.
- Store emits new state.
- Controllers/presenters receive new state and perform further logic, for example update view by calls to plugin.

Note that Renshi doesn't have [redux middleware mechanism](https://redux.js.org/advanced/middleware). Controllers are kind of middleware but not really. I consider adding middleware in the future releases.

#### The code

Android components extend Renshi base classes so you can add plugins to them. I recommend to share one store per context, so for example fragments should use theirs `Activity` store. Currently Renshi supports base classes for: `Activity`, `Fragment`, `DialogFragment`.
```kotlin
class MakeApiCallFragment : BaseFragment() {

    override val store: Store<*>
        get() = (activity as BaseActivity).store

    init {
        // Plugins must be added in constructor.
        plug(MakeApiCallBtnFragmentPluginImpl(this))
    }
}
```
`Fragment` (or `Activity`) specfic code goes to plugins. Even creating view in `onCreateView()`. Note that plugin is passive. It doesn't call controllers/presenters directly but emits events by using `PublishSubject`. In this example one plugin creates a controller and presenter, but you can use separate plugins. One for button clicks (would create controller) and one for hiding/showing button (would create presenter).
```kotlin
class MakeApiCallBtnFragmentPluginImpl(
        fragment: BaseFragment
) : BaseFragmentPlugin(fragment), MakeApiCallBtnFragmentPlugin {

    /**
     * Emits event on every button press.
     */
    override val makeApiCallClicks = PublishSubject.create<Unit>()!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_make_api_call, container, false).apply {
        makeApiCallBtn.setOnClickListener { makeApiCallClicks.onNext(Unit) }
    }

    override fun hideButton() {
        fragment.view!!.visibility = View.GONE
    }

    override fun showButton() {
        fragment.view!!.visibility = View.VISIBLE
    }

    /*
    We compose two independent controllers into one. You can split your controller logic as you wish.
     */
    override fun createController() = CompositeController(
            listOf(MakeApiCallControllerImpl(), MakeApiCallBtnPresenterImpl())
    )
}
```
Controllers survive orientation change and have two main references. One to `plugin` and one to `store`. To dispatch some action in your controller you just call `store.dispatch()`.
```kotlin
class MakeApiCallControllerImpl
: BaseController<MakeApiCallBtnFragmentPlugin, MainActivityState>(), MakeApiCallBtnController {

    private val makeApiCallRequests = PublishSubject.create<Unit>()

    init {
        // Stream is created in constructor because it should survive detaching from view.
        // We use disposeOnDestroy() to dispose stream when presenter is destroyed permanently.
        disposeOnDestroy(
                makeApiCallRequests
                        .flatMapCompletable {
                            store.dispatch(MakingApiCallAction())
                        }
                        .subscribe()
        )
    }

    override fun onAttachPlugin() {
        // We use disposeOnDetach() to dispose stream every time plugin is detached.
        disposeOnDetach(
                plugin!!
                        .makeApiCallClicks
                        .subscribeBy(
                                onNext = { makeApiCallRequests.onNext(Unit) }
                        )
        )
    }
}
```
Disptaching action will create new state in reducer.
```kotlin
class MakingApiCallReducer : Reducer<MakingApiCallAction, MainActivityState> {

    override fun reduce(action: MakingApiCallAction, state: MainActivityState) =
            Single
                    .just(state)
                    .map {
                        it.copy(
                                apiCallsCount = it.apiCallsCount + 1,
                                loading = true
                        )
                    }
}
```
You listen for new state in presenters and render it. Controllers and presenters inherit from the same class - `BaseController` but have two different responsibilities. Both handle plugin and state events, but only presenters should modify view by calling theirs plugin.
```kotlin
class MakeApiCallBtnPresenterImpl 
: BaseController<MakeApiCallBtnFragmentPlugin, MainActivityState>(), MakeApiCallBtnPresenter {

    override fun onAttachPlugin() {
        // we only want to listen for store changes when plugin is attached
        disposeOnDetach(
                store
                        .stateChanges
                        .map { it.loading }
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                                onNext = {
                                    if (it) plugin?.hideButton()
                                    else plugin?.showButton()
                                }
                        )
        )
    }
}
```
You can dispatch as many action types as you wish and create separate reducers for them by using `CompositeReducer`.
```kotlin
class MainActivityStore : BaseStore<MainActivityState>() {

    override val initialState = MainActivityState()

    // We compose many reducers into one root reducer.
    override val stateReducer = CompositeReducer<MainActivityState>().apply {
        // every reducer handles proper action type
        add(MakingApiCallAction::class, MakingApiCallReducer())
        add(FinishingApiCallAction::class, FinishingApiCallReducer())
    }
}
```
## Credits

Plugin mechanism is build on top of [CompositeAndroid](https://github.com/passsy/CompositeAndroid) library. I've also used Mateusz Koślacz passive view and multipresenter implementation idea that he uses in his library: [Moviper](https://github.com/mkoslacz/Moviper).

Great thanks to my friends for reviewing the library. Especially:

- Łukasz Wojtach



## License
```
Copyright 2018 Damian Chodorek

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```