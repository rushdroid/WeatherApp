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
    val ZIP_CODE = "380005,IN" // Replace zipcode
}
```

## This project follows best practices and utilizes the following libraries:

- Hilt: For dependency injection
- Retrofit: For making network requests
- Coroutine: For asynchronous programming
- Flow: For asynchronous data streams
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
```

This workflow will trigger on every push to the main branch, checking out the code, setting up Java 11, building the project with Gradle, and running unit tests.

Make sure to replace "REPLACE YOUR API KEY" with your actual OpenWeatherMap API key.

Feel free to reach out if you have any questions or need further assistance!
