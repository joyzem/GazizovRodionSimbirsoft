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
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
    implementation(project(":core:design-system"))
    implementation(project(":core:ui"))
    implementation(project(":core:di"))
    implementation(project(":core:data"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":utils"))

    implementation(project(":feature:auth"))
    implementation(project(":feature:help"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:search"))
    implementation(project(":feature:news"))
    implementation(project(":feature:news-details-api"))
    implementation(project(":feature:news-details-impl"))

    implementation(libs.androidx.splash.screen)
    implementation(libs.kotlinx.datetime)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
