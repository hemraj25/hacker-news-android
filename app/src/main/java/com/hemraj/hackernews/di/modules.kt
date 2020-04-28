package com.hemraj.hackernews.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hemraj.hackernews.data.HackerNewsDataRepository
import com.hemraj.hackernews.data.network.NewsNetworkApi
import com.hemraj.hackernews.presentation.home.HackerNewsViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://hn.algolia.com/api/"

val networkModule = module {
    factory { provideGson() }
    factory { provideAuthInterceptor() }
    factory { provideOkhttpLoggingInterceptor() }
    factory { provideOkhttpClient(get(), get()) }
    factory { provideRetrofitClient(get(), get()) }
    single { provideApiClient(get()) }
}

val hackerNewsRepositoryModule = module {
    factory { HackerNewsDataRepository(get()) }
}

val hackerNewsViewModel =  module {
    viewModel {
        HackerNewsViewModel(get())
    }
}

fun provideGson(): Gson {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    return gsonBuilder.create()
}

fun provideOkhttpLoggingInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun provideAuthInterceptor(): Interceptor {
    return Interceptor { chain ->
        val newRequest =  chain.request()
            .newBuilder()
            .addHeader("Source", "mobile")
            .build()

        chain.proceed(newRequest)
    }
}

fun provideOkhttpClient(okHttpLoggingInterceptor: HttpLoggingInterceptor,
                        authInterceptor: Interceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(okHttpLoggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()
}

fun provideRetrofitClient(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
}

fun provideApiClient(retrofit: Retrofit): NewsNetworkApi {
    return retrofit.create(NewsNetworkApi::class.java)
}