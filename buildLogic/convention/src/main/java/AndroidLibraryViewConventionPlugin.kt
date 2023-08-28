import com.android.build.gradle.LibraryExtension
import com.example.buildlogic.convention.configureAndroidView
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryViewConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.example.convention.library")
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidView(extension)
        }
    }

}