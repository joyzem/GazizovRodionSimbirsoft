import com.android.build.api.dsl.ApplicationExtension
import com.example.buildlogic.convention.configureAndroidView
import com.example.buildlogic.convention.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationViewConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            val extension = extensions.getByType<ApplicationExtension>()
            configureAndroidView(extension)
        }
    }

}