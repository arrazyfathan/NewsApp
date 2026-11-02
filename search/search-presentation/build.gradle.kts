plugins {
    alias(libs.plugins.newsapp.android.library)
    alias(libs.plugins.newsapp.android.hilt)
}

android {
    namespace = "com.arrazyfathan.search_presentation"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":common:common-utils"))
    implementation(project(":common:common-ui"))
    implementation(project(":search:search-data"))
    implementation(project(":search:search-domain"))

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

    // Glide
    implementation(libs.glide)
    kapt(libs.glide.compiler)

    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    // Lottie
    implementation(libs.lottie)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.shimmer)

    implementation(libs.flowbinding.android)
}
