plugins {
    id("com.android.application")
}

android {
    namespace = "com.huuduc.giuaky"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.huuduc.giuaky"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
// https://mvnrepository.com/artifact/androidx.core/core
    implementation ("androidx.core:core:1.13.0")
    // https://mvnrepository.com/artifact/com.itextpdf/itextpdf
    implementation ("com.itextpdf:itextpdf:5.5.13.3")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")


    implementation("com.android.volley:volley:1.2.1")
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    // https://mvnrepository.com/artifact/com.squareup.retrofit2/converter-gson
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    // https://mvnrepository.com/artifact/com.github.bumptech.glide/glide
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}