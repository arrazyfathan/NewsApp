plugins {
    alias(libs.plugins.newsapp.android.application)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.newsapp.android.hilt)
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.arrazyfathan.newsapp"

    defaultConfig {
        applicationId = "com.example.newsapp"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":feature:home:presentation"))
    implementation(project(":feature:home:data"))
    implementation(project(":feature:home:domain"))
    implementation(project(":feature:search:presentation"))
    implementation(project(":feature:search:data"))
    implementation(project(":feature:search:domain"))
    implementation(project(":feature:detail:presentation"))
    implementation(project(":core:utils"))

    implementation(fileTree("src/main/libs") { include("*.jar") })
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.google.material)

    implementation(libs.bundles.coroutines)

    implementation(libs.bundles.lifecycle)

    implementation(libs.bundles.room)
    kapt(libs.androidx.room.compiler)

    debugImplementation(libs.bundles.pluto.debug)
    releaseImplementation(libs.bundles.pluto.release)
}

kapt {
    correctErrorTypes = true
}
