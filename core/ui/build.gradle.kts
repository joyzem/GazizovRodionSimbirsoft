plugins {
    id("com.example.convention.library")
    id("com.example.convention.library.view")
    id("com.example.convention.dagger")
}

android {
    namespace = "com.example.androidpractice.core.ui"
}

dependencies {
    implementation(project(":core:design-system"))
    implementation(project(":mvi"))
    api(libs.io.coil)
}
