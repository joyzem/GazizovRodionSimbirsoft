import com.android.build.gradle.LibraryExtension
import com.example.buildlogic.convention.configureAndroidView
import com.example.buildlogic.convention.getLibsCatalog
import com.example.buildlogic.convention.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryViewConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidView(extension)
        }
    }

}