plugins {
    id("com.example.convention.library")
}

android {
    namespace = "com.example.utils"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.gson)
}
