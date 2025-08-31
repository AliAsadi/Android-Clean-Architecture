plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions.add("environment")

    productFlavors {
        create("prod") {
            applicationIdSuffix = ".prod"
            isDefault = true
        }
        create("mock") {
            applicationIdSuffix = ".mock"
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    kotlinOptions {
        freeCompilerArgs += listOf("-Xjvm-default=all")
    }

    namespace = "com.aliasadi.clean"
}

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
}

dependencies {
    implementation(project(":data"))
    testImplementation(project(":core-test"))

    // Kover Coverage (All Modules)
    kover(project(":data"))
    kover(project(":domain"))

    // Kotlin
    implementation(libs.kotlin.stdlib.jdk7)

    // AndroidX
    implementation(libs.appcompat)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Extensions
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.extensions)

    // okHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // GSON
    implementation(libs.gson)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // Hilt
    implementation(libs.hilt.dagger.android)
    implementation(libs.hilt.work)
    ksp(libs.hilt.dagger.compiler)
    ksp(libs.hilt.compiler)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Paging
    implementation(libs.paging.compose)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.ui.util)
    implementation(libs.compose.animation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.runtime.saveable)
    implementation(libs.compose.material3)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons.extended)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.accompanist.systemuicontroller)

    // Coil
    implementation(libs.coil.compose)

    // Navigation
    implementation(libs.navigation.compose)
    implementation(libs.hilt.navigation.compose)

    // Serialization
    implementation(libs.kotlinx.serialization.json)
}
