plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "lb.edu.ul.bikhedemtak"
    compileSdk = 35

    defaultConfig {
        applicationId = "lb.edu.ul.bikhedemtak"
        minSdk = 35
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

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.dotsindicator)
    implementation (libs.viewpager2)
    implementation (libs.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    implementation(libs.navigation.ui)
    implementation (libs.androidx.fragment.ktx)
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    implementation(libs.circleimageview)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.volley)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)

    implementation(libs.androidx.swiperefreshlayout)
    implementation (libs.androidx.navigation.fragment.ktx.v2xx)
    implementation (libs.androidx.navigation.ui.ktx.v2xx)
}