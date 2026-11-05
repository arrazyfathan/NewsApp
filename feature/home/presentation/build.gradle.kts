plugins {
    alias(libs.plugins.newsapp.android.library)
    alias(libs.plugins.newsapp.android.hilt)
}

android {
    namespace = "com.arrazyfathan.home.presentation"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core:utils"))
    implementation(project(":core:ui"))
    implementation(project(":feature:home:domain"))
    implementation(project(":feature:home:data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)
    /*testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)*/

    // Navigation Components
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Coroutine Lifecycle Scopes
    implementation(libs.bundles.lifecycle)

    // Glide
    implementation(libs.glide)
    kapt(libs.glide.compiler)

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    // Lottie
    implementation(libs.lottie)

    implementation(libs.androidx.paging.runtime)

    implementation(libs.androidx.swiperefreshlayout)

    implementation(libs.shimmer)

    implementation(libs.androidx.recyclerview)
}
