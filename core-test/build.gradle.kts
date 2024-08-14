plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
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

    namespace = "com.aliasadi.core.test"
}

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
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