import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
}

apply {
    from("$rootDir/codeAnalysis.gradle")
}

apply {
    from("$rootDir/jacoco.gradle")
}

android {
    namespace = "com.hemraj.hackernews"
    compileSdk = Config.compileSdk
    defaultConfig {
        applicationId = "com.hemraj.hackernews"
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("release") {
            val keystorePropertiesFile = rootProject.file("keystore.properties")
            val keystoreProperties = Properties().apply {
                load(keystorePropertiesFile.inputStream())
            }
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword =  keystoreProperties["keyPassword"] as String
            storeFile =  rootProject.file(keystoreProperties["storeFile"] as String)
            storePassword =  keystoreProperties["storePassword"] as String
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            enableUnitTestCoverage = true
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    compileOptions {
        sourceCompatibility = Config.sourceCompatibility
        targetCompatibility = Config.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        // https://developer.android.com/jetpack/androidx/releases/compose-kotlin
        kotlinCompilerExtensionVersion = "1.4.4"
    }
}


dependencies {
    implementation(Deps.appCompat)
    implementation(Deps.coreKtx)
    implementation(Deps.recyclerview)
    implementation(Deps.lifecycleViewModelKtx)
    implementation(Deps.lifecycleLiveDataKtx)

    implementation(Deps.retrofit)
    implementation(Deps.retrofitConverter)
    implementation(Deps.okHttp)
    implementation(Deps.okHttpInterceptor)

    implementation(Deps.lottie)
    //https://github.com/airbnb/lottie/blob/master/android-compose.md
    implementation(Deps.lottieCompose)
    implementation(Deps.koin)

    //********************* Testing *************************************
    testImplementation(Deps.jUnit)
    testImplementation(Deps.mockk)
    testImplementation(Deps.coreTesting)
    testImplementation(Deps.coroutineTest)
    androidTestImplementation(Deps.jUnitExt)
    androidTestImplementation(Deps.espressoCore)


    //********************* Jetpack Compose *************************************
    val composeBom = platform("androidx.compose:compose-bom:2023.03.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Material Design 3
    implementation("androidx.compose.material3:material3")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
