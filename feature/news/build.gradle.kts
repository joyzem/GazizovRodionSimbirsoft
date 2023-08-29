plugins {
    id("com.example.convention.feature")
}

android {
    namespace = "com.example.androidpractice.feature.news"
}

dependencies {
    implementation(project(":utils"))
    implementation(libs.kotlinx.datetime)
}
