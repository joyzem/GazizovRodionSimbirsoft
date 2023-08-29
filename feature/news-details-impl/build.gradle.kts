plugins {
    id("com.example.convention.feature")
}

android {
    namespace = "com.example.androidpractice.feature.news_details_impl"
}

dependencies {
    implementation(project(":feature:news-details-api"))
    implementation(project(":utils"))
    implementation(libs.kotlinx.datetime)
}