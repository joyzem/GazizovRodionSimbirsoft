import com.android.build.gradle.LibraryExtension
import com.example.buildlogic.convention.getLibsCatalog
import com.example.buildlogic.convention.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("kotlin-kapt")

            val libs = getLibsCatalog()
            extensions.getByType<LibraryExtension>().apply {
                dependencies {
                    implementation(libs,"androidx-room-runtime")
                    implementation(libs, "androidx-room-ktx")
                    add("kapt", libs.findLibrary("androidx.room.compiler").get())
                }
            }
        }
    }
}