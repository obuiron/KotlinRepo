buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha05")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx/")
        maven(url = "https://jitpack.io")
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
