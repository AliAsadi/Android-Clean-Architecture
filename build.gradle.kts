// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android) apply false
    alias(libs.plugins.kotlin) apply false
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
