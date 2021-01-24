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
    }

    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val androidGradle = "com.android.tools.build:gradle:${Version.androidGradle}"
    const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.navigation}"
}

dependencies {
    implementation(Plugins.kotlin)
    implementation(Plugins.androidGradle)
    implementation(Plugins.navigationSafeArgs)
}
