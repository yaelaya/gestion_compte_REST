plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "ma.ensaj.gestion_compte_retrofit"
    compileSdk = 34

    defaultConfig {
        applicationId = "ma.ensaj.gestion_compte_retrofit"
        minSdk = 30
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
}

dependencies {
    // AndroidX & UI
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espressoCore)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofitGson)
    implementation(libs.okhttpLogging)

    // Jackson pour JSON
    implementation(libs.jacksonDatabind)        // Sérialisation et désérialisation JSON
    implementation(libs.jacksonAnnotations)    // Annotations Jackson
    implementation(libs.jacksonCore)           // Core Jackson

    // Jackson pour XML
    implementation(libs.jacksonDataformatXml)

    // Retrofit avec Jackson (pour JSON)
    implementation(libs.retrofitJackson)

    // Retrofit avec SimpleXML (pour XML)
    implementation(libs.retrofitSimplexml)
}