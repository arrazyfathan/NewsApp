buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(uri("https://jitpack.io"))
    }
    dependencies {
        classpath(Deps.navigationSafeArgs)
        classpath(Deps.hiltAgp)
        classpath("com.android.tools.build:gradle:8.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
