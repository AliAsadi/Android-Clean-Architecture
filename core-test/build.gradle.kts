plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
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
    // UnitTest
    api(libs.mockito.kotlin)
    api(libs.mockito.inline)
    api(libs.mockito.android)
    api(libs.core.testing)
    api(libs.junit.junit)
    api(libs.turbine)
    api(libs.truth)
    api(libs.kotlinx.coroutines.test)
}