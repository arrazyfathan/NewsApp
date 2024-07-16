plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = DefaultConfig.compileSdk

    defaultConfig {
        applicationId = DefaultConfig.applicationId
        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk
        versionCode = DefaultConfig.versionCode
        versionName = DefaultConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        viewBinding = true
    }
    namespace = "com.example.newsapp"
}

dependencies {
    
    homePresentation()
    implementation(project(":home:home-data"))
    implementation(project(":home:home-domain"))
    implementation(project(":search:search-presentation"))
    implementation(project(":search:search-data"))
    implementation(project(":search:search-domain"))
    implementation(project(":detail:detail-presentation"))
    implementation(project(":common:common-utils"))

    implementation(fileTree("src/main/libs") { include("*.jar") })
    implementation(Deps.appCompat)
    implementation(Deps.core)
    implementation(Deps.constrainLayout)
    implementation(Deps.androidLegacySupport)
    implementation(Deps.material)

    implementation(Deps.coroutinesCore)
    implementation(Deps.coroutinesAndroid)

    implementation(Deps.viewModelKtx)
    implementation(Deps.lifecycleRuntime)

    hilt()

    implementation(Deps.roomKtx)
    implementation(Deps.roomRuntime)
    kapt(Deps.rookCompiler)
}

kapt {
    correctErrorTypes = true
}
