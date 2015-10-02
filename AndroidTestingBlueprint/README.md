# Android Testing Blueprint

A collection of Google's Android testing tools and frameworks, all integrated in a single application project.

## Structure:

- `app/` - Main application
- `app/test/` - Unit tests
- `app/androidTest/` - Instrumentation tests
- `app/androidTestFlavor2/` - Additional Instrumentation tests for Flavor2.
- `module-flavor1-androidTest-only/` - Test-only module with Instrumentation tests for Flavor1
- `module-android-library/` - An Android module (typically a library)
- `module-android-library/androidTest` - Android Tests for the module-android-library
- `module-plain-java/` - A Java module for non-Android code (business logic, utils classes, etc.)
- `module-plain-java/test` - Unit tests for module-plain-java

## Running Tests

### Running Instrumentation Tests

#### In Android Studio
- In the *Build Variants* window, make sure the *Android Instrumentation Tests* option is selected.
- Open a Instrumentation test class like `EspressoTest.java` or `UiAutomatorTest.java`
- Right click on the class and *Run* as Android Test.

#### From command-line via Gradle
- To run all the Instrumentation tests in the `app` module execute:

``` sh
./gradlew app:connectedAndroidTest
```

#### From command-line via adb
- Install the app and the test app on the device. For example:

``` sh
./gradlew app:installFlavor2Debug app:installFlavor2DebugAndroidTest
```

- To run the Instrumentation tests in the `app` module execute:

``` sh
adb shell am instrument -w com.example.android.testing.blueprint.flavor2.test/android.support.test.runner.AndroidJUnitRunner
```

See the [Modules](#modules), [Flavors](#flavors) and custom [Gradle command-line arguments](#custom-gradle-command-line-arguments) sections to learn how to execute tests for specific modules or flavors and pass custom arguments to AndroidJUnitRunner.

### Running Unit Tests
In Android Studio:
- In the *Build Variants* window, make sure the *Unit Tests* option is selected.
- Open a unit/local test unit class like `LocalUnitTest.java`.
- Right click on the class and *Run* as JUnit test.

From command-line via Gradle:
- To run all the local unit tests in `app` execute:

```
./gradlew app:test
```

- To run the local unit tests in a module execute:

```
./gradlew {module}:test
```

## Testing frameworks and APIs

### Espresso
Espresso is a part of the ATSL (Android Testing Support Library) and a framework for writing concise, beautiful, and reliable Android UI tests.

#### Espresso-Core
The core API is small, predictable, and easy to learn API which. Espresso enables  testing of state expectations, interactions, and assertions clearly without the distraction of boilerplate content, custom infrastructure, or messy implementation details getting in the way.

Espresso tests run optimally fast! Leave your waits, syncs, sleeps, and polls behind and let Espresso gracefully manipulate and assert on the application UI when it is at rest.

To get started with Espresso, refer to our [EspressoBasicSample](https://github.com/googlesamples/android-testing/tree/master/ui/espresso/BasicSample)

#### Espresso-Contrib
Espresso Contrib is an extension to Espresso which contains support for the most commonly used Android widgets and support library extension, for instance, RecyclerView.

#### Espresso-Intents
Espresso Intents is a great way to do hermetic inter app testing. It works essentially like [Mockito](https://code.google.com/p/mockito/) and allows for Intent verification and stubbing. To learn more, please refer to our Espresso Intents samples:
[IntentsBasicSample](https://github.com/googlesamples/android-testing/tree/master/ui/espresso/IntentsBasicSample)
[IntentsAdvancedSample](https://github.com/googlesamples/android-testing/tree/master/ui/espresso/IntentsAdvancedSample)

#### Espresso-Web
Espresso-web allows you to seamlessly test WebViews on Android. It uses the popular WebDriver API to introspect into, and control, the behavior of a WebView.  To learn more, please refer to our Espresso Web sample:
 Espresso Intents samples:
[WebBasicSample](https://github.com/googlesamples/android-testing/tree/master/ui/espresso/WebBasicSample)

### UIAutomator
UI Automator testing framework provides a set of APIs to build UI tests that perform interactions on user apps and system apps. The UI Automator APIs allow you to perform operations such as opening the Settings menu or the app launcher in a test device. The UI Automator testing framework is well-suited for writing black box-style automated tests, where the test code does not rely on internal implementation details of the target app.

To learn more about UIAutomator refer to the [BasicSample](https://github.com/googlesamples/android-testing/tree/master/ui/uiautomator/BasicSample)

## Modules

### Android Test-Only Module
When developing your application, it is sometimes easier to split production and test code into separate modules.

The structure of such a test-only module is very similar to developing a vanilla application. Instead of putting your instrumentation tests into the `androidTest` source set, put all test code into the `main` source set (`src/main/java`) and add an `AndroidManifest.xml` (`src/main`).

A test only module cannot have a test APK itself so it cannot have a `src/androidTest` folder. Also, there are no variants for test modules so if you have different flavors in your main application, you need a different test module for each variant you want to test.

Note: Only Instrumentation-based tests are allowed in a test-only module.

#### Running test-only module tests

##### From Android Studio
Run normally (see above).

##### From command-line via Gradle
Execute:

```
./gradlew module-flavor1-androidTest-only:connectedAndroidTest
```

##### From command-line via adb
You need to install the app and test app first:

```
./gradlew app:installFlavor1Debug module-flavor1-androidTest-only:installDebug
```

Execute the adb command:

```
adb shell am instrument -w com.example.android.testing.blueprint.test/android.support.test.runner.AndroidJUnitRunner
```

### Plain Java module
Moving code away from the Android framework is a good practice as it guarantees testability and relaxes coupling.

The module provided in `module-plain-java` has a class in `module-plain-java/src/main` and its own unit tests in `module-plain-java/src/test`.

Unit tests in a separate java module are run in the same way than unit tests in the app or any other module: run from Android Studio as a JUnit test or use the following gradle command:

```
./gradlew module-plain-java:test
```

## Flavors
This project has two: `flavor1` and `flavor2`. They simply have different application ID and R.string.app_name and they generate two APKs:
- `com.example.android.testing.blueprint.flavor1`
- `com.example.android.testing.blueprint.flavor2`

The `module-flavor1-androidTest-only` only targets flavor1 via the `targetVariant` in its build.gradle file.

The tests in `app/androidTestFlavor2` only target flavor2.

The rest of the tests in `app/androidTest` will be executed once per flavor one a connected Android Test is executed.

## Code coverage

### Unit tests
In Android Studio, code coverage for **unit tests** (executed as JUnit tests) is shown in the editor, next to each line. Run your tests using the *Run test with code coverage* option. JaCoCo test reports can be generated changing the *Coverage runner* in the JUnit test configuration.

From the command-line via Gradle, unit tests for the module-plain-java are generated by executing:

```
./gradlew module-plain-java:test module-plain-java:jacocoTestReport
```

A HTML report will be available in `module-plain-java/build/reports/jacoco/test/html/index.html`

Note: Generating JaCoCo reports from command-line via Gradle for the `app` module is currently only working for instrumentation but not for unit tests: [https://code.google.com/p/android/issues/detail?id=144664]

### Android tests
Test coverage reports for Android tests can be found in `app/build/reports/coverage`.

## ProGuard
By default tests are run against a non-minified version of your production APK. In most cases, especially during development, this makes a lot of sense because it speeds up your build and therefore improves your cycle times. However it is important to know about breakages as a result of a minified production APK, thus it makes sense to run your tests against a minified version of your APK.

Since Gradle Android Plugin version 1.2.0 using ProGuard has become a lot easier. A new `testProguardFile` property was added to the DSL which can be used to provide specific ProGuard rules for your test APK. When running ProGuard, Gradle will now use `testProguardFile` rules and apply it to the test code, but also apply the obfuscation mapping from the production APK run.
To try it out just uncomment the minify section for the debug build type in the app modules `build.gradle` file.

## Custom Gradle command-line arguments
Gradle allows you to pass custom arguments to `AndroidJUnitRunner`. This is equivalent to running `adb shell am instrument -w -e <argName> <argValue> com.example.android.testing.blueprint.test/android.support.test.runner.AndroidJUnitRunner`.
Custom arguments can be particularly useful when you just want to run a specific test class/method/qualifier.

To pass a custom argument the -Pcom.android.tools.instrumentationTestRunnerArgs=argName=argValue
property needs to be used, in conjunction with `argName` and `argValue`. Multiple custom arguments require one property definition per argument.

For instance, to run all tests annotated with the `@Large` test size qualifier in the app module, execute:

``` sh
./gradlew app:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.size=large
```

To only run tests for a specific test class, i.e. EspressoTest, execute:

``` sh
./gradlew app:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.example.android.testing.blueprint.ui.espresso.EspressoTest
```

To pass in an arbitrary argument which can be accessed in a test at runtime, execute:

``` sh
./gradlew module-flavor1-androidTest-only:connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.argument1=make_test_fail
```
All arguments passed through command line can also be specified in the project's build.gradle file, which is
great for specifying values which are required by the test harness itself. The `argument1` from the previous
example could also be permanently added using the `testInstrumentationRunnerArgument` property in the build.gradle file.
Look at the app modules build.gradle file for usage.

See [AndroidJUnitRunner](http://developer.android.com/reference/android/support/test/runner/AndroidJUnitRunner.html) documentation to learn more about custom command-line arguments.

## Support
- Google+ Community: https://plus.google.com/communities/105153134372062985968
- Stack Overflow: http://stackoverflow.com/questions/tagged/android-testing

If you've found an error in this sample, please file an issue:
https://github.com/googlesamples/android-testing-blueprint

## Contributions
Patches are encouraged, and may be submitted by forking this project and
submitting a pull request through GitHub. Please see CONTRIBUTING.md for more details.

## Bugs and issues
Issues for this project should only be filed for problems with the actual samples.

For issues against the build tools use [b.android.com](https://b.android.com). **Pro-tip**: fork this project with a failing test or broken build and include the URL in your bug report. This makes bug description, triaging and debugging much easier.

## License
Copyright 2015 The Android Open Source Project, Inc.

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements.  See the NOTICE file distributed with this work for
additional information regarding copyright ownership.  The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License.  You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
License for the specific language governing permissions and limitations under
the License.
