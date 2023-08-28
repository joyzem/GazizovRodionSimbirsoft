plugins {
    id("com.example.convention.feature")
}

android {
    namespace = "com.example.androidpractice.feature.auth"
}

dependencies {
    implementation(project(":core:di"))
}
