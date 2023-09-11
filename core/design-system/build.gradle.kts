plugins {
    id("com.example.convention.library.view")
}

android {
    namespace = "com.example.androidpractice.core.designsystem"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
}

dependencies {
    implementation(libs.androidx.splash.screen)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    debugImplementation(libs.androidx.compose.tooling.preview)
    implementation(libs.androidx.compose.tooling)
}
