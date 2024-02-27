# Weather App README

Welcome to our Weather App project! This README will guide you through the folder structure, provide information about constant files, and explain how to use GitHub Actions for building and testing.

## Folder Structure

## Constant Files

Constant files are located in `app/src/main/java/com/example/weatherappdemo/util/Constants.kt`:

You can replase

```kotlin
object Constants {
    val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val API_KEY = "REPLACE YOUR API KEY" // Replace your api key
    val COUNTRY_CODE = "IN" // Replace country code for which you want to get data
}
```

### To create an OpenWeatherMap API key, follow these steps:

Go to the OpenWeatherMap website: [OpenWeatherMap](https://openweathermap.org/).
1. Sign up for an account if you don't have one. If you already have an account, you can sign in.
2. After signing in, go to the API keys section. You can find it by clicking on your username at the top right corner and selecting "My API keys" from the dropdown menu.
3. Click on the "Create API key" button.
4. Enter a name for your API key (e.g., "My Weather App").
4. Select the subscription plan that suits your needs. OpenWeatherMap offers various plans, including free and paid options. For development and testing purposes, you can start with the free plan.
5. Click on the "Generate" button to create your API key.
6. Once your API key is generated, you'll see it listed on the API keys page. It will look something like this: "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx".
7. Copy your API key and store it securely. You'll need to use this API key in your application to authenticate your requests to the OpenWeatherMap API.

That's it! You've successfully created an OpenWeatherMap API key. You can now use this key in your application to access weather data from the OpenWeatherMap API.

## This project follows best practices and utilizes the following libraries:

- Hilt: For dependency injection
- Retrofit: For making network requests
- Coroutine: For asynchronous programming
- Flow: For asynchronous data streams
- Jetpack compose: For User Interface
- JUnit: Unit test cases
- Github Actions: For build and test unit test cases
- Other best practices for Android development


## GitHub Actions

We use GitHub Actions for generating builds and running unit tests. The workflow is defined in .github/workflows/android-ci.yml:

```
name: Android CI
on: [push]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v4.1.0
      - name: Set up JDK 11
        uses: actions/setup-java@v3.13.0
        with:
          distribution: 'adopt'
          java-version: '17'

      - name: Grant execute permissions for gradlew
        run: chmod +x ./gradlew

      - name: Run Tests with Gradle
        run: ./gradlew test
      - name: Unit Test
        run: ./gradlew testDebugUnitTest
      - name: Build with Gradle
        run: ./gradlew assembleDebug
      - name: Archive Debug APK
        uses: actions/upload-artifact@v2
        with:
          name: debug-apk
          path: app/build/outputs/apk/debug/*.apk
```

This workflow will trigger on every push to the main branch, checking out the code, setting up Java 11, building the project with Gradle, and running unit tests and generate the debug build.

Make sure to replace "REPLACE YOUR API KEY" with your actual OpenWeatherMap API key.

Feel free to reach out if you have any questions or need further assistance!
