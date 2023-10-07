plugins {
    id("com.example.convention.feature")
}

android {
    namespace = "com.example.androidpractice.feature.news"

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
}

dependencies {
    implementation(project(":utils"))
    implementation(project(":feature:news-details:news-details-api"))
    implementation(libs.kotlinx.datetime)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.io.coil.compose)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.tooling.preview)


    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.test.junit4)
    debugImplementation(libs.androidx.compose.test.manifest)
    debugImplementation(libs.androidx.compose.tooling)
}
