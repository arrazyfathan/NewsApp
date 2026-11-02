import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.newsapp.android.library)
    alias(libs.plugins.newsapp.android.hilt)
}

android {
    namespace = "com.arrazyfathan.common_utils"

    val apiKey: String = gradleLocalProperties(rootDir, providers).getProperty("API_KEY")

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "API_KEY", "\"$apiKey\"")
        }
    }
}

dependencies {
    implementation(project(":common:common-ui"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.timeago)
    /*testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)*/

    // Retrofil
    implementation(libs.bundles.networking)

    implementation(libs.glide)
    implementation(libs.glide.compiler)
    implementation(libs.androidx.swiperefreshlayout)

    debugImplementation(libs.bundles.pluto.debug)
    releaseImplementation(libs.bundles.pluto.release)
}
