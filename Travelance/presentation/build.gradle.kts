plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.moneyminions.presentation"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":domain"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // coil
    implementation("io.coil-kt:coil:2.2.2")
    implementation("io.coil-kt:coil-compose:2.2.2")

    // navigation
    var nav_version = "2.5.3"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.31.1-alpha")

    // constraintLayout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // viewmodel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // livedata
    implementation("androidx.compose.runtime:runtime-livedata:1.5.0")

    implementation("androidx.compose.runtime:runtime-android:1.5.0")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.3.3")

    // Hilt
    var dagger_version = "2.45"
    var hilt_version = "1.0.0"
    implementation("com.google.dagger:hilt-android:$dagger_version")
    kapt("com.google.dagger:hilt-android-compiler:$dagger_version")
    implementation("androidx.hilt:hilt-navigation-compose:$hilt_version")

    // retrofit & okhttp
    implementation("com.squareup.retrofit2:converter-scalars:2.5.0")
    implementation("com.squareup.retrofit2:adapter-rxjava:2.1.0")
    implementation("com.google.code.gson:gson:2.8.6")

    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")

    // Pager
    implementation("com.google.accompanist:accompanist-pager:0.20.1")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.20.1")

    // calendar
    implementation("com.kizitonwose.calendar:compose:2.4.0-beta01")

    // https://mvnrepository.com/artifact/androidx.viewpager2/viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // kakao
    implementation("com.kakao.sdk:v2-all:2.14.0")

    // 네이버 지도 SDK
    implementation("com.naver.maps:map-sdk:3.17.0")
    implementation("io.github.fornewid:naver-map-compose:1.3.3")
    // GPS
    implementation("com.google.android.gms:play-services-location:21.0.1")
    // Permission
    implementation("com.google.accompanist:accompanist-permissions:0.28.0")
    // Lottie
    implementation("com.airbnb.android:lottie-compose:6.1.0")

    // 애니메이션
    implementation("androidx.compose.foundation:foundation:1.1.0")
    implementation("androidx.compose.animation:animation:1.1.0")

    // 생체인증
    implementation("androidx.biometric:biometric:1.1.0")

    // Kotlin
    implementation("androidx.biometric:biometric:1.2.0-alpha05")

    // 카드 flip
    implementation("com.wajahatkarim:flippable:1.0.6")

    // system bars customization
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")

    //TedPermission
    implementation ("io.github.ParkSangGwon:tedpermission-normal:3.3.0")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.2.2"))

    // Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

}
