import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Created by Ar Razy Fathan Rabbani on 28/09/23.
 */

fun DependencyHandler.implementation(dependency: String) {
    add("implementation", dependency)
}

fun DependencyHandler.implementation(dependency: Dependency) {
    add("implementation", dependency)
}

fun DependencyHandler.testImplementation(dependency: String) {
    add("testImplementation", dependency)
}

fun DependencyHandler.androidTest(dependency: String) {
    add("androidTest", dependency)
}

fun DependencyHandler.debugImplementation(dependency: String) {
    add("debugImplementation", dependency)
}

fun DependencyHandler.kapt(dependency: String) {
    add("kapt", dependency)
}