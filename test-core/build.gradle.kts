plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    flavorDimensions += "environment"

    productFlavors {
        create("prod") {
            isDefault = true
        }
        create("mock")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    namespace = "com.aliasadi.core.test"
}

dependencies {
    implementation(project(":core"))
    // UnitTest
    api("org.mockito.kotlin:mockito-kotlin:4.0.0")
    api("org.mockito:mockito-inline:5.0.0")
    api("org.mockito:mockito-android:5.0.0")
    api("androidx.arch.core:core-testing:2.2.0")
    api("junit:junit:4.13.2")
    api("app.cash.turbine:turbine:0.12.1")
    api("com.google.truth:truth:1.1.3")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
}