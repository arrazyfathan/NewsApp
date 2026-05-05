buildscript {
    repositories {
        google()
        mavenCentral()
        maven(uri("https://jitpack.io"))
    }
    dependencies {
        classpath(Deps.navigationSafeArgs)
        classpath(Deps.hiltAgp)
        classpath("com.android.tools.build:gradle:8.13.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
