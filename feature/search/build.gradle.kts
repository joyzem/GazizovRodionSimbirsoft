plugins {
    id("com.example.convention.feature")
}

android {
    namespace = "com.example.androidpractice.feature.search"
}

dependencies {
    implementation(project(":utils"))
}
