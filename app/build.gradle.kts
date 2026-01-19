plugins {
    alias(libs.plugins.android.application)
    // Removed org.jetbrains.kotlin.android to resolve conflict with AGP 9.0's Built-in Kotlin
}

android {
    namespace = "com.example.quizzy"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.quizzy"
        minSdk = 24
        targetSdk = 36
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

    // Modern way to configure Kotlin in AGP 9.0+ with builtInKotlin enabled (the default)
    //noinspection WrongGradleMethod
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx) // Fixed: changed '-' to '.'
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    
    // Glide for loading images from URL
    implementation("com.github.bumptech.glide:glide:4.16.0")
    
    // Lottie for animations
    implementation("com.airbnb.android:lottie:6.7.1")
    
    // Konfetti for celebration
    implementation("nl.dionsegijn:konfetti-xml:2.0.5")
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
