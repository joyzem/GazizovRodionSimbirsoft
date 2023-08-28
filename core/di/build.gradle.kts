plugins {
    id("com.example.convention.library")
    id("com.example.convention.dagger")
}

android {
    namespace = "com.example.androidpractice.core.di"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel)
}
