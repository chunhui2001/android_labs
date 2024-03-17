plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.android_labs.androidstudiotutorial"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android_labs.androidstudiotutorial"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.google.code.gson:gson:2.8.7")
    implementation("com.squareup.picasso:picasso:2.5.2")

    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.5.0")
    implementation("com.android.volley:volley:1.2.1")

    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("de.hdodenhof:circleimageview:3.0.0")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.flaviofaria:kenburnsview:1.0.7")

    implementation("com.gauravk.audiovisualizer:audiovisualizer:0.9.2")

    // Jetpack Compose
    implementation("androidx.compose.ui:ui:1.6.4")
//    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material:1.6.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.4")
    implementation("androidx.activity:activity-compose:1.8.2")

    // System UI Controller - Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")
    implementation(libs.androidx.work.runtime.ktx)

    implementation("com.google.android.gms:play-services-location:21.2.0")

    kapt("androidx.room:room-compiler:2.6.1")
}