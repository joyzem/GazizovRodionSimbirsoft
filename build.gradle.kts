import org.jlleitschuh.gradle.ktlint.tasks.GenerateReportsTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.0" apply false
    id("com.android.library") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.4.0"
}

ext {
    val sdkVersion = 33
    val appcompatVersion = "1.6.1"
    val retrofitVersion = "2.9.0"
}

tasks.withType(GenerateReportsTask::class.java) {
    reportsOutputDirectory.set(project.layout.buildDirectory.dir("build/reports/ktlint"))
}
