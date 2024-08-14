plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
}

android {
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    flavorDimensions.add("environment")

    productFlavors {
        create("prod") {
            isDefault = true
        }
        create("mock")
    }

    namespace = "com.aliasadi.domain"
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(libs.paging.common.ktx)
}