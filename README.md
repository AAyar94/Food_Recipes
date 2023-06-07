# Food_Recipes

I build this app with [Stevdza-San](https://github.com/stevdza-san) 's [Modern Food Recipes App](https://www.udemy.com/course/modern-food-recipes-app-android-development-with-kotlin/) Udemy course and tried to add Material Desing 3 and my knowledge

![Screenshot](https://redwerk.com/wp-content/uploads/2019/11/cover_Kotlin.png)

Food Recipes app; **Dagger Hilt**, **Data Binding**, **DataStore**,**ROOM**, **Coroutines**, **Lifecycle**, **LiveData**, **ViewModel**, **Retrofit**, **Coil** based on **MVVM** Architecture.

Food Recipes from : [Spoonacular API](https://spoonacular.com/food-api)

## Setup

If you want to try this project, before running, be sure you added your Spoonacular API key local properties.

```js
FOOD_RECIPES_API_KEY=COPY_YOUR_API_KEY_HERE
```


## Architecture 🏛

**Model - View - ViewModel (MVVM)** is the industry recognized software architecture pattern that overcomes all drawbacks of MVP and MVC design patterns. MVVM suggests separating the data presentation logic(Views or UI) from the core business logic part of the application

![Screenshot](https://androidwave.com/wp-content/uploads/2019/05/mvvm-architecture-app-in-android.png)


## Libraries 🛠 ⚙️
- [Kotlin](https://github.com/JetBrains/kotlin) -> The Kotlin Programming Language.
- [Data binding](https://developer.android.com/topic/libraries/data-binding) -> The Data Binding Library is a support library that allows you to bind UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
- [Data Store](https://developer.android.com/topic/libraries/architecture/datastore) -> Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers. DataStore uses Kotlin coroutines and Flow to store data asynchronously, consistently, and transactionally.
- [Retrofit](https://github.com/square/retrofit) -> A type-safe HTTP client for Android and the JVM.
- [OkHttp](https://github.com/square/okhttp) -> Square’s meticulous HTTP client for the JVM, Android, and GraalVM.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) -> Library support for Kotlin coroutines
- [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) -> Perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) -> Hilt provides a standard way to incorporate Dagger dependency injection into an Android application.
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) -> LiveData is an observable data holder class.
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) -> ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way.
- [Coil](https://coil-kt.github.io/coil/) -> An image loading library for Android backed by Kotlin Coroutines

## Android 📱 Application Screens 📸

<img src="https://raw.githubusercontent.com/AAyar94/Food_Recipes/master/Screenshots/Mockup1.png" />
<img src="https://raw.githubusercontent.com/AAyar94/Food_Recipes/master/Screenshots/Mockup2.png" />

## License ℹ️
```
MIT License

Copyright (c) 2023 Adem Ayar

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
