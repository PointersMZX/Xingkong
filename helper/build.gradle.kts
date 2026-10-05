plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.xingkong.helper"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.xingkong.helper"
        // 故意低 minSdk/targetSdk：与原 App helper 一致，
        // 配合 ADB 装 `pm install -r -t --bypass-low-target-sdk-block` 在 shell/system UID 下运行
        minSdk = 21
        targetSdk = 26
        versionCode = 1
        versionName = "1.0.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        aidl = true
    }

    lint {
        // targetSdk 26 是故意的（特权 helper，配合 pm install --bypass-low-target-sdk-block；
        // 不上 Google Play，原 App helper 同款）
        disable += "ExpiredTargetSdkVersion"
    }

    signingConfigs {
        create("release") {
            // 公益版先用 debug.keystore 签名（与主 App 同款），正式上架前换自有 keystore
            storeFile = file("../app/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
}
