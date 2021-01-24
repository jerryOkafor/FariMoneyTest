/**
 * Author: Jerry
 * Project: FairmoneyTest
 */

/**Default and Adroid Config*/
object Config {
    object Version {
        const val minSdkVersion = 21
        const val compileSdkVersion = 30
        const val targetSdkVersion = 30
        const val versionName = "1.0"
        const val versionCode = 1
    }

    const val isMultiDexEnabled = true

    object Android {
        const val applicationId = "com.example.testapp"
        const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

/**Help handle group of libraries*/
interface Libraries {
    val components: List<String>
}

object Dependencies {
    object Versions {
        const val okHttp = "4.8.1"
    }

    object Desugar : Libraries {
        object Version {
            const val desugar = "1.0.9"
        }

        const val desugaring = "com.android.tools:desugar_jdk_libs:${Version.desugar}"

        override val components = listOf(desugaring)

    }


    object Kotlin {
        object Versions {
            const val kotlin = "1.4.21"
            const val coroutines = "1.4.2"
        }

        const val stdlib: String = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        const val coroutineCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutineAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val coroutineTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"


    }

    object AndroidX : Libraries {
        object Versions {
            const val core = "1.3.2"
            const val lifeCycle = "2.2.0"
            const val appCompat = "1.2.0"
            const val archCoreTesting = "2.1.0"
            const val swipeRefresh = "1.1.0"
            const val fragment = "1.2.5"
        }

        const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
        const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragment}"
        const val appComppat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val lifeCycleCommon =
            "androidx.lifecycle:lifecycle-common-java8:${Versions.lifeCycle}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycle}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycle}"
        const val swipeRefresh =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"

        const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTesting}"
        override val components = listOf(coreKtx, lifeCycleCommon, viewModel, appComppat)
    }

    object Room : Libraries {
        object Version {
            const val room = "2.2.6"
        }

        const val roomRuntime = "androidx.room:room-runtime:${Version.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Version.room}"
        const val roomKtx = "androidx.room:room-ktx:${Version.room}"
        const val roomTesting = "androidx.room:room-testing:${Version.room}"
        override val components = listOf(roomRuntime, roomCompiler, roomKtx, roomTesting)

    }

    object Dagger : Libraries {
        object Version {
            const val dagger = "2.31.2"
        }

        const val dagger = "com.google.dagger:dagger:${Version.dagger}"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Version.dagger}"
        const val daggerAndroid = "com.google.dagger:dagger-android:${Version.dagger}"
        const val daggerAndroidSupport =
            "com.google.dagger:dagger-android-support:${Version.dagger}"
        const val daggerAndroidCompiler =
            "com.google.dagger:dagger-android-processor:${Version.dagger}"
        override val components = listOf(
            dagger, daggerCompiler, daggerAndroid, daggerAndroidSupport,
            daggerAndroidCompiler
        )
    }


    object Network : Libraries {
        object Versions {
            const val retrofit = "2.9.0"
        }

        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

        override val components = listOf(retrofit, gsonConverter)
    }

    object Navigation : Libraries {
        object Version {
            const val navigation = "2.3.0"
        }

        const val navigationFragmentKtx =
            "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
        const val navigationUiKtx =
            "androidx.navigation:navigation-ui-ktx:${Version.navigation}"

        override val components =
            listOf(navigationFragmentKtx, navigationFragmentKtx)
    }

    /**View*/
    object View : Libraries {
        private object Versions {
            const val material = "1.2.1"
            const val constraintLayout = "2.0.4"
        }

        const val material = "com.google.android.material:material:${Versions.material}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

        override val components = listOf(material, constraintLayout)
    }

    /**Utils*/
    object Utils : Libraries {
        private object Versions {
            const val timber = "4.7.1"
            const val glide = "4.11.0"


        }

        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
        const val glide = ("com.github.bumptech.glide:glide:${Versions.glide}")
        const val glideCompiler = ("com.github.bumptech.glide:compiler:${Versions.glide}")

        override val components = listOf(timber)
    }


    object Test : Libraries {
        object Versions {
            const val junit = "4.13"
            const val mockitoCore = "3.7.7"
        }

        const val mockitoCore = "org.mockito:mockito-android:${Versions.mockitoCore}"
        const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoCore}"
        const val junit = "junit:junit:${Versions.junit}"
        const val mockWebServer =
            "com.squareup.okhttp3:mockwebserver:${Dependencies.Versions.okHttp}"

        override val components = listOf(junit, mockWebServer)
    }

    object AndroidTest : Libraries {
        object Versions {
            const val testCore = "1.3.0"
            const val testRunner = "1.3.0"
            const val testRules = "1.3.0"
            const val testExtJunit = "1.1.2"
            const val testExtTruth = "1.3.0"
            const val espresso = "3.2.0"
            const val benchmark = "1.0.0"
            const val orchestrator = "1.3.1-alpha03"
        }

        const val testExtJunitKtx = "androidx.test.ext:junit-ktx:${Versions.testExtJunit}"
        const val testCoreKtx = "androidx.test:core-ktx:${Versions.testCore}"
        const val testRunner = "androidx.test:runner:${Versions.testRunner}"
        const val testRules = "androidx.test:rules:${Versions.testRules}"
        const val testExtTruth = "androidx.test.ext:truth:${Versions.testExtTruth}"

        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"

        const val junitBenchmark = "androidx.benchmark:benchmark-junit4:${Versions.benchmark}"
        const val orchestrator = "androidx.test:orchestrator:${Versions.orchestrator}"

        override val components =
            listOf(espresso, junitBenchmark, testRunner, testCoreKtx)
    }
}