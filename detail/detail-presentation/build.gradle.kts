plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.arrazyfathan.detail_presentation"
    compileSdk = DefaultConfig.compileSdk

    defaultConfig {
        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(Deps.core)
    implementation(Deps.appCompat)
    implementation(Deps.material)
    implementation(Deps.constrainLayout)
    testImplementation(TestImplementation.jUnit)
    androidTestImplementation(AndroidTestImplementation.jUnit)
    androidTestImplementation(AndroidTestImplementation.espresso)

    // Hilt
    implementation(DaggerHilt.hilt)
    kapt(DaggerHilt.hiltCompiler)
    kapt(DaggerHilt.hiltAndroidCompiler)

    implementation(project(":common:common-utils"))
    implementation(project(":common:common-ui"))
    implementation(project(":detail:detail-article-domain"))
    implementation(project(":home:home-domain"))
}