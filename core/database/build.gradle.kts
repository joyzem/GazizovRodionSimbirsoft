plugins {
    id("com.example.convention.library")
    id("com.example.convention.room")
    id("com.example.convention.dagger")
}

android {
    namespace = "com.example.androidpractice.core.database"
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(project(":core:model"))
}
