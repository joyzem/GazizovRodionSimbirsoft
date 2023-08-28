plugins {
    id("com.example.convention.library.view")
}

android {
    namespace = "com.example.androidpractice.core.designsystem"
}

dependencies {
    implementation(libs.androidx.splash.screen)
}
