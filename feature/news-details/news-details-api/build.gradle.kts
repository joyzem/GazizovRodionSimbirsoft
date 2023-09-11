plugins {
    id("com.example.convention.library")
}

android {
    namespace = "com.example.androidpractice.feature.news_details_api"
}

dependencies {
    implementation(libs.androidx.fragment)
    implementation(project(":core:ui"))
}
