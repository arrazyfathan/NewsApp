
Today News App
==
Today News App is a modular Android application built to showcase clean architecture, modularization, and MVVM design pattern principles. It provides a user-friendly interface to browse and read the latest news articles from various categories such as business, technology, sports, entertainment, and more.

Screenshots
-----------  
![News App](screenshot/img.png "News App Screenshot")


Technologies Used: :
--------------  
* **Clean Architecture**: The project implements the principles of clean architecture to separate the business, presentation, and data layers. This enables easier testing and flexibility in swapping out layer implementations.
* **Modularization**: The application employs a modularization approach to divide the project into separate modules, such as domain, presentation, and data. Each module has clear responsibilities and can be developed independently.
* **MVVM (Model-View-ViewModel)**: The MVVM architecture is utilized to separate the business logic from the view. The Model represents data and business rules, the View is the user-visible interface, and the ViewModel is responsible for preparing and delivering data to the View.
* LiveData
* Room Database
* **Lottie** - Render After Effects animations natively on Android and iOS, Web, and React Native ([Lottie](https://github.com/airbnb/lottie-android))
* Kotlin Flow
* **Flow Binding** ([FlowBinding](https://github.com/ReactiveCircus/FlowBinding) - Kotlin Coroutines Flow binding APIs for Android's platform and unbundled UI widgets, inspired by RxBinding.)
* **buildSrc** : for dependency management to centralize and simplify dependencies configuration.
* Kotlin Coroutines
* Glide
* Navigation Component
* ViewModel
* Lifecycles Components
* Retrofit
* **Paging 3** ([Paging Documentation](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=id))
* **Dagger Hilt** for dependency injection and managing object dependencies. ([Dagger Hilt Docs](https://dagger.dev/hilt/))

News API key
------------  
Today News App uses the [News API](https://newsapi.org/docs/get-started) to load news. To use the API, you will need to obtain a free developer API key. See the  
[News API Documentation](https://newsapi.org/docs) for instructions.

### Setup API Key
Add api key into your `local.properties` file:

```gradle  
API_KEY=YOUR_API_KEYS  
```  

Demo
-----------

https://github.com/arrazyfathan/NewsApp/assets/18359255/0da726ae-bc1d-46ec-9b9c-1f467dc86734

https://github.com/arrazyfathan/NewsApp/assets/18359255/084fee83-5267-4ceb-b8b2-ae4f111f9cb8

https://github.com/arrazyfathan/NewsApp/assets/18359255/3529ff02-02b9-43ce-bbae-86954da93e9a


# License

```xml
MIT License

Copyright (c) [2021] [Ar Razy Fathan Rabbani]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
