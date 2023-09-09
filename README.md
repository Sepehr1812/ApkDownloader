# APK Downloader
A simple Android app that receives an application's data from the server and displays its details. The user can download and install the application on the device.

## Application Scenario
1. On each app launch, the app has to solve a puzzle to get an authentication token from the server. The app requests the server an array of numbers. They represent price changes of an imaginary stock.
2. The app has to find the maximum revenue achieved in the best trading scenario for that price list.
3. The app sends the answer to the server, and the server sends an authentication token to the app if the correct answer has been sent.
4. The app requests an application's data with the auth token. The application's data includes the title of the app, its icon image, and a URL to download its APK file.
5. The application's data is displayed to the user. The user can download the application and install it. Also, the user can pause and resume the downloading process.

## Networking Details
The `BASE_URL` address is not included in the git files using the [Gradle Secret Plugin](https://github.com/google/secrets-gradle-plugin). Also, I've used the [PRDownloader](https://github.com/amitshekhariitbhu/PRDownloader) library to download APK files and implement pause/resume functionalities.

## Architecture and Technologies
In this project, I benefited from several Android concepts and libraries, including:
* Jetpack Compose
* Coroutines
* State Flows
* Retrofit
* Hilt
* Coil
* Moshi
* Gradle Secret Plugin
* JUnit (for the puzzle solver algorithm's unit tests)

Also, I implemented this project with the **MVVM** pattern using **clean architecture** principles.
