import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */

object Version {
    const val core = "1.2.0"
    const val appCompat = "1.1.0"
    const val constrainLayout = "1.1.3"
    const val legacySupport = "1.0.0"
    const val material = "1.4.0"
    const val viewModelKtx = "2.3.1"
    const val room = "2.6.1"
    const val coroutines = "1.5.1"
    const val lifecycleRuntime = "2.2.0"
    const val retrofit = "2.6.0"
    const val loggingInterceptor = "4.5.0"
    const val navigation = "2.7.7"
    const val glide = "4.11.0"
    const val timeAgo = "4.0.3"
    const val lottie = "4.1.0"
    const val dagger = "2.44"
    const val hiltCompiler = "1.0.0"
    const val okHttp = "4.9.0"
    const val activityKtx = "1.8.1"
    const val fragmentKtx = "1.5.6"
    const val paging = "3.3.0"
    const val shimmer = "0.5.0"
    const val swipeRefresh = "1.1.0"
    const val recyclerView = "1.3.0"
    const val navigationSafeArgs = "2.5.3"

    const val kotlinCoroutinesTest = "1.6.4"
    const val testImplJunit = "4.13.2"
    const val robolectrict = "4.9.2"
    const val testCoreKtx = "1.5.0"
    const val androidTestImplJunit = "1.1.1"
    const val androidTestImplEspresso = "3.2.0"

    const val plutoVersion = "2.2.2"
}

object Deps {
    const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.navigationSafeArgs}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Version.dagger}"
    const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
    const val core = "androidx.core:core-ktx:${Version.core}"
    const val constrainLayout =
        "androidx.constraintlayout:constraintlayout:${Version.constrainLayout}"
    const val androidLegacySupport = "androidx.legacy:legacy-support-v4:${Version.legacySupport}"
    const val material = "com.google.android.material:material:${Version.material}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.viewModelKtx}"
    const val roomRuntime = "androidx.room:room-runtime:${Version.room}"
    const val rookCompiler = "androidx.room:room-compiler:${Version.room}"
    const val roomKtx = "androidx.room:room-ktx:${Version.room}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
    const val lifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleRuntime}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Version.okHttp}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Version.loggingInterceptor}"
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
    const val glide = "com.github.bumptech.glide:glide:${Version.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Version.glide}"
    const val timeAgo = "com.github.marlonlom:timeago:${Version.timeAgo}"
    const val lottie = "com.airbnb.android:lottie:${Version.lottie}"
    val activityKtx by lazy { "androidx.activity:activity-ktx:${Version.activityKtx}" }
    val fragmentKtx by lazy { "androidx.fragment:fragment-ktx:${Version.fragmentKtx}" }
    val paging by lazy { "androidx.paging:paging-runtime:${Version.paging}" }
    val shimmer by lazy { "com.facebook.shimmer:shimmer:${Version.shimmer}" }
    val swipeRefresh by lazy { "androidx.swiperefreshlayout:swiperefreshlayout:${Version.swipeRefresh}" }
    val recyclerView by lazy { "androidx.recyclerview:recyclerview:${Version.swipeRefresh}" }
}

object DaggerHilt {
    const val hilt = "com.google.dagger:hilt-android:${Version.dagger}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Version.dagger}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Version.hiltCompiler}"
}

fun DependencyHandler.hilt() {
    implementation(DaggerHilt.hilt)
    kapt(DaggerHilt.hiltCompiler)
    kapt(DaggerHilt.hiltAndroidCompiler)
}

fun DependencyHandler.homePresentation() {
    implementation(project(":home:home-presentation"))
}

fun DependencyHandler.pluto() {
    debugImplementation("com.plutolib:pluto:${Version.plutoVersion}")
    releaseImplementation("com.plutolib:pluto-no-op:${Version.plutoVersion}")
    debugImplementation("com.plutolib.plugins:bundle-core:${Version.plutoVersion}")
    releaseImplementation("com.plutolib.plugins:bundle-core-no-op:${Version.plutoVersion}")

    debugImplementation("com.plutolib.plugins:network-interceptor-okhttp:${Version.plutoVersion}")
    releaseImplementation("com.plutolib.plugins:network-interceptor-okhttp-no-op:${Version.plutoVersion}")
}

fun DependencyHandler.testing() {
    testImplementation(TestImplementation.jUnit)
    testImplementation(TestImplementation.kotlinCoroutinesTest)
    testImplementation(TestImplementation.robolectrict)
    testImplementation(TestImplementation.testCoreKtx)
}

object TestImplementation {
    const val jUnit = "junit:junit:${Version.testImplJunit}"
    const val kotlinCoroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.kotlinCoroutinesTest}"
    const val robolectrict = "org.robolectrict:robolectrict:${Version.robolectrict}"
    const val testCoreKtx = "androidx.test:core-ktx:${Version.testCoreKtx}"
}

object AndroidTestImplementation {
    const val jUnit = "androidx.test.ext:junit:${Version.androidTestImplJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Version.androidTestImplEspresso}"
}
