plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.dndcompanionapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.dndcompanionapp"
        minSdk = 25
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
    // Use BOM (Bill of Materials) for Compose dependencies to ensure compatibility between libraries
    implementation(platform(libs.androidx.compose.bom)) // Automatically manages Compose library versions

    // Core Compose Libraries
    dependencies {

        implementation(libs.ui)
        implementation(libs.androidx.material)
        implementation(libs.ui.tooling.preview)
        implementation(libs.androidx.activity.compose.v131)
        // Additional dependencies for icons and UI previews

        // Extended Material Icons for Compose (needed for icons like ArrowDropUp/ArrowDropDown)


        // Check for the latest version


        // Kotlin & Lifecycle libraries
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        dependencies {
            implementation(libs.coil.compose) // Coil for Jetpack Compose
            implementation(libs.coil.gif) // Coil support for GIFs (if using GIF images)
        }

        // Jetpack Compose Activity for integrating with Compose UI
        implementation(libs.androidx.activity.compose)
        implementation(libs.support.annotations)
        implementation(libs.androidx.material3.android)
        implementation(libs.androidx.navigation.runtime.ktx)
        implementation(libs.androidx.navigation.common)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.preference.ktx)

        // Testing Libraries
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        //androidTestImplementation(libs.androidx.compose.ui.test.junit4)

        // Debugging tools for Compose UI
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
    }
}
