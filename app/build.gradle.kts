// File: build.gradle.kts (Module :app)

plugins {
    alias(libs.plugins.android.application)
    // WAJIB: Tambahkan plugin google-services untuk Firebase
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.projekgabunganpam"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.projekgabunganpam"
        minSdk = 28
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
}

dependencies {
    // Dependensi dasar (sudah bersih dari duplikasi)
    implementation(libs.appcompat)
    implementation(libs.material) // Menggunakan alias dari TOML file, lebih konsisten

    // Dependensi pihak ketiga
    implementation("com.github.chrisbanes:PhotoView:2.3.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // --- PERBAIKAN UTAMA ADA DI BAWAH INI ---
    // Menggunakan alias yang sudah kita definisikan di libs.versions.toml
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth) // Untuk Google Sign-In
    implementation(libs.googleid)           // Untuk One Tap Sign-In
    implementation(libs.activity.ktx)        // Menggantikan libs.activity
    implementation(libs.constraintlayout)

    // Dependensi untuk Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}