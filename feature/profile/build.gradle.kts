plugins {
    id("com.example.convention.feature")
}

android {
    namespace = "com.example.androidpractice.feature.profile"
}

dependencies {
    // RecyclerView matchers
    androidTestImplementation(libs.androidx.test.espresso.contrib)
}
