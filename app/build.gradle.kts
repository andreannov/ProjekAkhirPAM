plugins {
    alias(libs.plugins.android.application)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
    dependencies {
        // Dependensi dasar
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation("com.github.chrisbanes:PhotoView:2.3.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("androidx.cardview:cardview:1.0.0")
        implementation("com.github.bumptech.glide:glide:4.16.0")
        annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

        // Firebase BoM
        implementation(platform(libs.firebase.bom))

        // [PERBAIKAN] Tambahkan library App Check di sini
        implementation(libs.firebase.appcheck.playintegrity)

        // Dependensi Firebase lainnya
        implementation(libs.firebase.auth)
        implementation(libs.play.services.auth)
        implementation(libs.googleid)
        implementation(libs.activity.ktx)
        implementation(libs.constraintlayout)

        // Dependensi untuk Testing
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
    }
