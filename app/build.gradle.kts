plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.dagger.hilt.android)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization)
}

android {
    namespace = "test.example.websocket"
    compileSdk = 35

    defaultConfig {
        applicationId = "test.example.websocket"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.google.dagger.hilt.android)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.io.ktor.client.core)
    implementation(libs.io.ktor.client.okhttp)
    implementation(libs.io.ktor.client.websockets)
    implementation(libs.io.ktor.client.logging)
    implementation(libs.io.ktor.serialization.kotlinx.json)

    implementation(libs.io.ktor.client.cio)
    implementation(libs.io.socket.io.client)

    implementation(libs.pipecat.client.gemini)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.io.ktor.client.content.negotiation)

    ksp(libs.google.dagger.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}