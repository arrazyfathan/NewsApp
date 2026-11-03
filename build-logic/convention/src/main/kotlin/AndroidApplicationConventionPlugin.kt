import com.android.build.api.dsl.ApplicationExtension
import com.arrazyfathan.newsapp.buildlogic.configureKotlinAndroid
import com.arrazyfathan.newsapp.buildlogic.intVersion
import com.arrazyfathan.newsapp.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.intVersion("targetSdk")
            }
        }
    }
}
