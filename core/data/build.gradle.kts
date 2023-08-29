plugins {
    id("com.example.convention.library")
    id("com.example.convention.dagger")
}

android {
    namespace = "com.example.androidpractice.core.data"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":utils"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.gson)
}
