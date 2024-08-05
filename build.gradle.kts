// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kover) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    // Apply Detekt plugin for all modules (presentation, data, domain)
    apply(from = "$rootDir/detekt.gradle")
    // Apply Kover plugin for all modules
    apply(from = "$rootDir/kover.gradle")
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
