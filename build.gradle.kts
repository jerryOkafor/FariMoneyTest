import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories.applyDefault()
}

allprojects {
    repositories.applyDefault()
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.withType<Delete>() {
    delete = setOf(rootProject.buildDir)
}
