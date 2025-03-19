plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
}

android {
    namespace = "ir.ncis.filmbuff"
    compileSdk = 35

    defaultConfig {
        applicationId = "ir.ncis.filmbuff"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    annotationProcessor(libs.androidx.room.compiler)
    debugImplementation(libs.chucker)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.viewpager)
    implementation(libs.converter.gson)
    implementation(libs.hawk)
    implementation(libs.material)
    implementation(libs.picasso)
    implementation(libs.retrofit)
    implementation(libs.tapsell.plus.sdk.android)
    ksp(libs.androidx.room.compiler)
    releaseImplementation(libs.chucker.no.op)
}