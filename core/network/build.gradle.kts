plugins {
    id("com.example.convention.library")
    id("com.example.convention.dagger")
}

android {
    namespace = "com.example.androidpractice.core.network"
}

dependencies {
    implementation(project(":core:model"))
    api(libs.squareup.retrofit2)
    api(libs.squareup.converter.gson)
    implementation(libs.squareup.logging.interceptor)
    implementation(libs.squareup.okhttp3)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.datetime)
}
