
# Android Clean Architecture [![Codacy Badge](https://api.codacy.com/project/badge/Grade/3e9bbcba7b3945f4b9ba0887b8639a00)](https://app.codacy.com/gh/AliAsadi/Android-Clean-Architecture?utm_source=github.com&utm_medium=referral&utm_content=AliAsadi/Android-Clean-Architecture&utm_campaign=Badge_Grade_Settings)

A demo movie android app showcasing Clean Architecture, written in Kotlin and featuring Jetpack Compose for modern, declarative UIs. 💯🎞

![image](https://github.com/AliAsadi/Android-Clean-Architecture/assets/20803775/afc6c445-8a0d-4cdc-a2de-0672be74c04a)



# Features

1. **Offline-first**: The app can be accessed even without an internet connection.
2. **Pagination**: Efficiently loads large amounts of data to improve the user experience.
3. **Search functionality**: Allows users to quickly find specific information within the app.
4. **Auto Sync**: Uses both NetworkConnectivityStream and WorkManager to ensure data is always up-to-date.
5. **Favorites**: Users can add movies to a favorites list.

## Previous Versions
* To explore the app's earlier **XML-based UI**, check out the [xml_based_app](https://github.com/AliAsadi/Android-Clean-Architecture/tree/xml_based_app
) branch.

* For a basic version of the app **without pagination**, check out the [xml_based_basic_app](https://github.com/AliAsadi/Android-Clean-Architecture/tree/xml_based_basic_app
) branch.

## The Motivation behind the app
This repository was created with the intention of sharing knowledge, stepping outside of my comfort zone, and using it to implement new challenges and ideas.

## Movies Mock Server API

**API:** [Movies Mock Server](https://movies-mock-server.vercel.app/)

This API is the primary data source for the app, providing a mock database of movie information. It is designed to mimic real-world data operations including fetching, updating, and managing movie data.

**API Repository:** [Movies Mock Server Repository](https://github.com/AliAsadi/movies-mock-server)

This repository hosts the source code and documentation for the Movies Mock Server API

## 💡 Architectural Insights
Architecture by its nature is **dynamic** and **ever-evolving**, there are always several solutions to every problem, and what works best will depend on the specific requirements and constraints of your project.

![image](https://user-images.githubusercontent.com/20803775/214686254-9405504c-05d2-417e-9cf5-669a1a57e8a6.png)

# Screenshots

![image](screenshot/screenshot-cover.png)

# Clean Architecture

The core principles of the clean approach can be summarized as followed:

#### 1. The application code is separated into layers.

These layers define the separation of concerns inside the code base.

#### 2. The layers follow a strict dependency rule.

Each layer can only interact with the layers below it.

#### 3. As we move toward the bottom layer — the code becomes generic.

The bottom layers dictate policies and rules, and the upper layers dictate implementation details such as the database, networking manager, and UI.

<p align="center">
<img src="https://raw.githubusercontent.com/AliAsadi/Android-Clean-Architecture/master/screenshot/architecture0.png">
</p>

# Architecture Layers

The application consists of three layers:

The domain layer, the data layer, and the presentation layer.

Looking at project’s high-level structure, you’ll see that each layer is represented by a module in the project.

![image](https://user-images.githubusercontent.com/20803775/201078111-39ba8e8d-b116-4312-bee0-df2d3258be71.png)

I like it because it helps me avoid accidentals “leaks” between the layers.


# Structure
<p align="center">
<img src="https://raw.githubusercontent.com/AliAsadi/Android-Clean-Architecture/master/screenshot/structure0.png">
</p>

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - A live data replacement.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern toolkit for building native UIs.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [Paging3](https://kotlinlang.org/) - Load and display small chunks of data at a time.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [SavedStateHandle](https://developer.android.com/reference/androidx/lifecycle/SavedStateHandle) - A handle to saved state passed down to androidx.lifecycle.ViewModel.
  - [Navigation Components](https://developer.android.com/guide/navigation/navigation-getting-started) - Navigate fragments easier.
  - [Room](https://developer.android.google.cn/jetpack/androidx/releases/room) - Persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
  - [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - Schedule deferrable, asynchronous tasks
  
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
  - [Hilt](https://dagger.dev/hilt) - Easier way to incorporate Dagger DI into Android application.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Mockito](https://github.com/mockito/mockito) - For Mocking and Unit Testing

## 💎 Code Style

The code style in this project was ensured using [Detekt](https://detekt.dev/).

**Check command:**

```
./gradlew detekt
```

## License
```
   Copyright (C) 2020 Ali Asadi
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

## Contributing
Just make pull request. You are in!
