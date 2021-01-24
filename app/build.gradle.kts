import Config.Version
import Dependencies.AndroidTest
import Dependencies.AndroidX
import Dependencies.Dagger
import Dependencies.Kotlin
import Dependencies.Navigation
import Dependencies.Network
import Dependencies.Room
import Dependencies.Test
import Dependencies.Utils
import Dependencies.View


plugins {
    applyAndroidApplication
    applyKotlinAndroid
    applyKotlinKapt
    applyNavSafeArgs
}


android {
    compileSdkVersion(Version.compileSdkVersion)

    defaultConfig {
        applicationId = Config.Android.applicationId
        minSdkVersion(Version.minSdkVersion)
        targetSdkVersion(Version.targetSdkVersion)
        versionCode = Version.versionCode
        versionName = Version.versionName

        testInstrumentationRunner = Config.Android.testInstrumentationRunner
        multiDexEnabled = true
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        animationsDisabled = true

        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }


    buildTypes {
        named("debug") {
            isDebuggable = true
            //isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            isTestCoverageEnabled = true
        }

        named("release") {
            isMinifyEnabled = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            isTestCoverageEnabled = false
        }
    }

    buildTypes.all {
        buildConfigField("String", "APP_ID", "\"6002612e0ae2ab802f631447\"")
    }

    sourceSets {
        val androidTest by named("androidTest")
        val test by named("test")

        getByName("main") {
            assets {
                srcDirs("src/main/assets", "src/main/resources", "src/sharedTest/assets")
            }
        }

        androidTest.apply {
            java {
                srcDirs("$projectDir/schemas", "src/sharedTest/java")
            }
        }

        test.apply {
            assets {
                srcDirs("src/test/resources")
            }
            java {
                srcDirs("src/sharedTest/java")
            }
        }
    }

    @Suppress("UnstableApiUsage")
    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    useLibrary("android.test.runner")
    useLibrary("android.test.base")
    useLibrary("android.test.mock")

    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }
}

kapt {
    correctErrorTypes = true
    generateStubs = true
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    coreLibraryDesugaring(Dependencies.Desugar.desugaring)

    implementation(Kotlin.stdlib)
    implementation(Kotlin.coroutineCore)
    implementation(Kotlin.coroutineAndroid)
    debugImplementation(Kotlin.coroutineTest)


    implementation(AndroidX.coreKtx)
    implementation(AndroidX.fragmentKtx)
    debugImplementation(AndroidX.fragmentTesting) {
        exclude("androidx.test", "core")
    }

    implementation(AndroidX.lifeCycleCommon)
    implementation(AndroidX.liveData)
    implementation(AndroidX.viewModel)
    implementation(AndroidX.swipeRefresh)

    implementation(View.material)
    implementation(View.constraintLayout)

    /**Room DB*/
    implementation(Room.roomRuntime)
    implementation(Room.roomKtx)
    kapt(Room.roomCompiler)

    /**Utils*/
    implementation(Utils.timber)
    implementation(Utils.glide)
    kapt(Utils.glideCompiler)

    /**Navigation*/
    implementation(Navigation.navigationFragmentKtx)
    implementation(Navigation.navigationUiKtx)

    /**DI*/
    implementation(Dagger.dagger)
    kapt(Dagger.daggerCompiler)
    implementation(Dagger.daggerAndroid)
    implementation(Dagger.daggerAndroidSupport)
    kapt(Dagger.daggerAndroidCompiler)

    /**Retrofit*/
    Network.components.forEach { implementation(it) }

    /**Test*/
    testImplementation(AndroidTest.testRunner)
    testImplementation(AndroidTest.testCoreKtx)
    testImplementation(AndroidTest.testRules)
    testImplementation(AndroidTest.testExtJunitKtx)
    testImplementation(AndroidTest.testExtTruth)
    testImplementation(Test.mockitoInline)
    testImplementation(Test.mockWebServer)

    testImplementation(Kotlin.coroutineCore)

    testImplementation(AndroidTest.espresso)
    testImplementation(AndroidTest.espressoContrib)

    testImplementation(AndroidX.archCoreTesting)
    testImplementation(Room.roomTesting)
    testImplementation(Test.robolectric)


    /**Android Test*/
    androidTestImplementation(AndroidTest.testExtJunitKtx)

    androidTestImplementation(AndroidTest.espresso)
    androidTestImplementation(AndroidTest.espressoContrib)


}