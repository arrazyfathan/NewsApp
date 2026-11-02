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

    implementation(project(":home:home-presentation"))
    implementation(project(":home:home-data"))
    implementation(project(":home:home-domain"))
    implementation(project(":search:search-presentation"))
    implementation(project(":search:search-data"))
    implementation(project(":search:search-domain"))
    implementation(project(":detail:detail-presentation"))
    implementation(project(":common:common-utils"))

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
