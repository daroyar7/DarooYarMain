plugins {
    id("com.android.application")
}

android {
    namespace = "com.health.darooyar"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.health.darooyar"
        minSdk = 26
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(files("./lib/material-spinner-1.3.1.aar"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.multidex:multidex:2.0.1")
    androidTestImplementation("com.google.android.exoplayer:exoplayer:2.18.1")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("com.google.android.exoplayer:exoplayer:2.18.1")
}