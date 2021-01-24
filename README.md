# Fairmoney Test
Fairmoney coding tes for sn. Android Developer Role

![Master - Run Tests and Build APK](https://github.com/jerryOkafor/fair_money_test/workflows/Master%20-%20Run%20Tests%20and%20Build%20APK/badge.svg)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

```
Android Studio >= 4.1.2
```

### Installing

Follow this steps if you want get a local copy of the project in your machine.

##### 1. Clone or fork the repository by running the cammand below.

```
git@github.com:jerryOkafor/fair_money_test.git

```
##### 2. Import the project in AndroidStudio.
1. In Android Studio, go to File -> New -> Import project
2. Selecte the folder and click on `open`.
3. Androidstudio imports the project.


## Running the tests

### Instrumentation tests

Use the command below to run Instrumentation test.

```
./gradlew connectedDebugAndroidTest
```

### Running Local Unit tests
Use the command below to run Junit test

```
./gradlew testDebugUnitTest --stacktrace
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Kotlin](https://kotlinlang.org/) - Koltin for JVM
* [Room Database](https://developer.android.com/topic/libraries/architecture/room) - The Room persistence library for Android.
* Dagger - DI
* Navigation
* Retrofit - Networking
* Robolectric - Testing
* JUnit - Testing

## Authors

* **Jerry Hanks**

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
