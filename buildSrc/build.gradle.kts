plugins {
    `kotlin-dsl`
}

repositories {
    maven("https://plugins.gradle.org/m2/")
    maven("https://jitpack.io")
    google()
    jcenter()
    mavenCentral()
}

object Plugins {
    object Version {
        const val kotlin = "1.4.21"
        const val androidGradle = "4.1.2"
        const val navigation = "2.3.2"
        const val daggerHiltAndroid = "2.28-alpha"
        const val ktLint = "9.3.0"
        const val detekt = "1.15.0"
        const val jacoco = "0.8.6"
        const val benchmark = "1.0.0"
    }

    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val androidGradle = "com.android.tools.build:gradle:${Version.androidGradle}"
    const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.navigation}"
    const val daggerHilt =
        "com.google.dagger:hilt-android-gradle-plugin:${Version.daggerHiltAndroid}"
    const val ktLint = "org.jlleitschuh.gradle:ktlint-gradle:${Version.ktLint}"
    const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Version.detekt}"
    const val jacoco = "org.jacoco:org.jacoco.core:${Version.jacoco}"
    const val benchmark = "androidx.benchmark:benchmark-gradle-plugin:${Version.benchmark}"
}

dependencies {
    implementation(Plugins.kotlin)
    implementation(Plugins.androidGradle)
    implementation(Plugins.navigationSafeArgs)
    implementation(Plugins.daggerHilt)
    implementation(Plugins.ktLint)
    implementation(Plugins.detekt)
    implementation(Plugins.jacoco)
    implementation(Plugins.benchmark)
}
