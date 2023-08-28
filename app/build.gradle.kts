plugins {
    id("com.example.convention.application")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.example.convention.dagger")
    id("com.example.convention.application.view")
}

android {
    namespace = "com.example.androidpractice"

    defaultConfig {
        applicationId = "com.example.androidpractice"
        minSdk = 23
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":mvi"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:design-system"))
    implementation(project(":core:ui"))
    implementation(project(":core:di"))
    implementation(project(":core:data"))
    implementation(project(":utils"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":feature:auth"))

    implementation("io.reactivex.rxjava3:rxjava:3.1.6")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("com.jakewharton.rxbinding4:rxbinding:4.0.0")
    implementation("com.jakewharton.rxbinding4:rxbinding-appcompat:4.0.0")

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("io.coil-kt:coil:2.4.0")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
