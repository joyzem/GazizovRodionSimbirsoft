plugins {
    id("com.example.convention.feature")
}

android {
    namespace = "com.example.androidpractice.feature.news"
}

dependencies {
    implementation(project(":utils"))
    implementation(project(":feature:news-details:news-details-api"))
    implementation(libs.kotlinx.datetime)
}
