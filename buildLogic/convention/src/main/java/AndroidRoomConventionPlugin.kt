import com.example.buildlogic.convention.getLibsCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("kotlin-kapt")

            val libs = getLibsCatalog()
            dependencies {
                add("implementation", libs.findLibrary("androidx-room-runtime").get())
                add("implementation", libs.findLibrary("androidx-room-ktx").get())
                add("kapt", libs.findLibrary("androidx.room.compiler").get())
            }
        }
    }
}