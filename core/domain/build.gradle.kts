plugins {
    id("com.example.convention.kotlin")
    id("com.example.convention.dagger")
}

dependencies {
    implementation(project(":core:model"))
}
