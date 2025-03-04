plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "2.0.20"
    id("com.google.devtools.ksp")
}

android {
    namespace = "myapplication.android.pixelpal"
    compileSdk = 35

    kapt {
        generateStubs = true
    }

    defaultConfig {
        applicationId = "myapplication.android.pixelpal"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.glide)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.firestore)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.analytics)
    implementation(libs.gson)
    implementation(libs.imagepicker)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.glide)
    implementation(libs.jp.glide.transformations)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.cicerone)
    implementation(libs.jp.glide.transformations)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.play.services.base)
    implementation (libs.dagger.v252)
    implementation (libs.dagger.android)
    kapt (libs.dagger.compiler.v252)
    kapt (libs.dagger.android.processor)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    androidTestImplementation(libs.androidx.espresso.core)
}