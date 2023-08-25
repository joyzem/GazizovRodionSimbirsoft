package com.example.buildlogic.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidView(
    commonExtension: CommonExtension<*, *, *, *>
) {
    val libs = getLibsCatalog()

    commonExtension.apply {
        buildFeatures {
            viewBinding = true
        }

        dependencies {
            implementation(libs, "androidx-core")
            implementation(libs, "androidx-fragment")
            implementation(libs, "androidx-appcompat")
            implementation(libs, "androidx-activity")
            implementation(libs, "androidx-constraint")
            implementation(libs, "androidx-legacy")
            implementation(libs, "androidx-lifecycle-livedata")
            implementation(libs, "androidx-lifecycle-viewmodel")
            implementation(libs, "androidx-legacy")
            implementation(libs, "androidx-legacy")
            implementation(libs, "google-material")
            implementation(libs, "reactivex-rxjava3")
            implementation(libs, "reactivex-rxandroid")
            implementation(libs, "jakewharton-rxbinding4")
            implementation(libs, "jakewharton-rxbinding4-appcompat")
        }
    }
}