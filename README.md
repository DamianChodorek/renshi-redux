# Renshi
### Highly composable [redux](https://redux.js.org/) library for Android.
## Why Renshi?

- Redux is predictable state container. It helps you write applications that behave consistently in multithreaded environment and are easy to test.
- Plugin mechanism helps you easily scale your code, by following open/closed principle. Delegate all code from Activities/Fragments/Services/Views to as many plugins as you wish.
- Every plugin can be further extended by attaching as many controllers/presenters as you wish.
- Renshi is easily extensible. It enables you to write your own plugins and redux components.
- You can pick any part of the library that is interesting to you and forget about the rest.
- Renshi is written in TDD, 100% Kotlin, based on RxJava and great library called [CompositeAndroid](https://github.com/passsy/CompositeAndroid).

## Project structure

- `renshi-core` - contains all basic interfaces and redux components.
- `renshi-fragments` - contains plugins and base classes for `Fragment` and `DialogFragment`.
- `sample-app` - example app that shows how to use Renshi in most efficient and recommended way (along with tests). It shows how to use redux to communicate two fragments with each other.
- `sample-simple-app-without-plugins` - simple example that shows how to use pure redux to communicate two fragments with each other. This is NOT recommended way of using Renshi because without plugins you may end up with a lot of code in your activities or fragments that won't scale.
- `sample-custom-plugins` - this example shows how to make your own plugins and plug them into any class that you need. If for some reason you can't extend Renshi base store owners (for example: BaseActivity), you can create your own store owner and delegate it's methods to plugins.

To learn more about advantages of redux visit [official redux website](https://redux.js.org/).

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