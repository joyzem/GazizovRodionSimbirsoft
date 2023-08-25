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
                apply("com.example.convention.view")
            }

            dependencies {
//                implementation(project(":core:model"))
//                implementation(project(":core:ui"))
//                implementation(project(":core:mvi"))
//                implementation(project(":core:data"))
//                implementation(project(":core:common"))
//                implementation(project(":core:domain"))
                // etc.
            }
        }
    }
}
