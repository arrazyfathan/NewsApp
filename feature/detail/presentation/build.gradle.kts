plugins {
    alias(libs.plugins.newsapp.android.library)
    alias(libs.plugins.newsapp.android.hilt)
}

android {
    namespace = "com.arrazyfathan.detail.presentation"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)
    /*testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)*/

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Coroutine Lifecycle Scopes
    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.lottie)

    implementation(project(":core:utils"))
    implementation(project(":core:ui"))
    implementation(project(":feature:detail:domain"))
    implementation(project(":feature:home:domain"))
}
