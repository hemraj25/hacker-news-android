object Deps {

    // Plugins
    const val jacocoCore = "org.jacoco:org.jacoco.core:${Versions.Jacoco}"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.AppCompat}"
    const val googleMaterial = "com.google.android.material:material:${Versions.GoogleMaterial}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.CoreKtx}"
    const val gson = "com.google.code.gson:gson:${Versions.Gson}"

    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.RecyclerView}"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Lifecycle}"
    const val lifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Lifecycle}"

    //Networking
    const val retrofit = "com.squareup.okhttp3:okhttp:${Versions.Retrofit}"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.Retrofit}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.OkHttp}"
    const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.OkHttp}"

    const val lottie = "com.airbnb.android:lottie:${Versions.Lottie}"
    const val lottieCompose = "com.airbnb.android:lottie-compose:${Versions.Lottie}"
    const val koin = "io.insert-koin:koin-android:${Versions.Koin}"

    //Testing
    const val jUnit = "junit:junit:${Versions.JUnit}"
    const val jUnitExt = "androidx.test.ext:junit:${Versions.JUnitExt}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.EspressoCore}"
    const val mockk = "io.mockk:mockk:${Versions.Mockk}"
    const val coreTesting = "androidx.arch.core:core-testing:${Versions.CoreTesting}"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Coroutine}"

}
