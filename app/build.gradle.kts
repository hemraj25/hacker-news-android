import java.util.*

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

apply {
    from("$rootDir/codeAnalysis.gradle")
}

apply {
    from("$rootDir/jacoco.gradle")
}

apply {
    from("$rootDir/detekt.gradle")
}

android {
    namespace = "com.hemraj.hackernews"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.hemraj.hackernews"
        minSdk =  libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
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

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }

    composeOptions {
        // https://developer.android.com/jetpack/androidx/releases/compose-kotlin
        kotlinCompilerExtensionVersion = libs.versions.composeKotlinCompiler.get()
    }
}


dependencies {

    implementation(libs.appCompat)
    implementation(libs.googleMaterial)
    implementation(libs.coreKtx)
    implementation(libs.gson)
    implementation(libs.recyclerview)
    implementation(libs.lifecycleViewModelKtx)
    implementation(libs.lifecycleLiveDataKtx)
    implementation(libs.lottie)
    //https://github.com/airbnb/lottie/blob/master/android-compose.md
    implementation(libs.lottieCompose)
    implementation(libs.koin)

    //********************* Networking *************************************
    implementation(libs.retrofit)
    implementation(libs.retrofitConverter)
    implementation(libs.okHttp)
    implementation(libs.okHttpInterceptor)

    //********************* Testing *************************************
    testImplementation(libs.jUnit)
    androidTestImplementation(libs.jUnitExt)
    androidTestImplementation(libs.espressoCore)
    testImplementation(libs.mockk)
    testImplementation(libs.coreTesting)
    testImplementation(libs.coroutineTest)

    //********************* Jetpack Compose *************************************
    val composeBom = platform(libs.composeBom)
    implementation(composeBom)
    testImplementation(composeBom)
    androidTestImplementation(composeBom)

    // Material Design 3
    implementation(libs.material3)

    // Android Studio Preview support
    implementation(libs.uiToolingPreview)
    debugImplementation(libs.uiTooling)

    // UI Tests
    androidTestImplementation(libs.uiTestJunit4)
    debugImplementation(libs.uiTestManifest)
}
