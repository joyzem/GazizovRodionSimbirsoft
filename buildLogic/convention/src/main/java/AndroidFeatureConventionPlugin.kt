import com.example.buildlogic.convention.getLibsCatalog
import com.example.buildlogic.convention.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.example.convention.library")
                apply("com.example.convention.dagger")
                apply("com.example.convention.library.view")
            }

            val libs = getLibsCatalog()
            dependencies {
                implementation(project(":core:model"))
                implementation(project(":core:ui"))
                implementation(project(":core:di"))
                implementation(project(":core:design-system"))
                implementation(project(":mvi"))
                implementation(project(":core:domain"))

                add("testImplementation", libs.findLibrary("org-robolectric").get())
                add("testImplementation", libs.findLibrary("androidx-test-core-ktx").get())
                add("testImplementation", libs.findLibrary("androidx-test-ext-junit").get())
                add("testImplementation", libs.findLibrary("io-mockk").get())
                add("testImplementation", libs.findLibrary("androidx-arch-core-testing").get())
                add("testImplementation", libs.findLibrary("junit4").get())
                add("androidTestImplementation", libs.findLibrary("androidx-test-espresso").get())
                add("androidTestImplementation", libs.findLibrary("androidx-test-ext").get())
                // etc.
            }
        }
    }
}
