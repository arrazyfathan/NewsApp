plugins {
    alias(libs.plugins.newsapp.android.library)
    alias(libs.plugins.newsapp.android.hilt)
}

android {
    namespace = "com.arrazyfathan.home.data"
}

dependencies {
    implementation(project(":core:utils"))
    implementation(project(":feature:home:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
    /*testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)*/

    // Retrofil
    implementation(libs.bundles.networking)
    implementation(libs.androidx.paging.runtime)

    // Room
    implementation(libs.bundles.room)
    kapt(libs.androidx.room.compiler)
}
