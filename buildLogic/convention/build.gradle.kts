plugins {
    `kotlin-dsl`
}

group = "com.example.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.example.convention.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationView") {
            id = "com.example.convention.application.view"
            implementationClass = "AndroidApplicationViewConventionPlugin"
        }
        register("androidDagger") {
            id = "com.example.convention.dagger"
            implementationClass = "AndroidDaggerConventionPlugin"
        }
        register("androidRoom") {
            id = "com.example.convention.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidLibrary") {
            id = "com.example.convention.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryView") {
            id = "com.example.convention.library.view"
            implementationClass = "AndroidLibraryViewConventionPlugin"
        }
        register("androidFeature") {
            id = "com.example.convention.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("kotlinModule") {
            id = "com.example.convention.kotlin"
            implementationClass = "KotlinModuleConventionPlugin"
        }
    }
}