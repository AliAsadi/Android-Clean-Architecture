// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kover) apply false
}

subprojects {
    repositories {
        google()
        mavenCentral()
    }
    // Apply Detekt plugin for all modules (presentation, data, domain)
    apply(from = "$rootDir/detekt.gradle")
    // Apply Kover plugin for all modules
    apply(from = "$rootDir/kover.gradle")
}