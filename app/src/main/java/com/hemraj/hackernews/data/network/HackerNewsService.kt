package com.hemraj.hackernews.data.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.hemraj.hackernews.data.HackerNewsApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object HackerNewsService {

    //Interceptor
    private val authInterceptor = Interceptor { chain ->
        val newRequest =  chain.request()
            .newBuilder()
            .addHeader("Source", "mobile")
            .build()

        chain.proceed(newRequest)
    }

    private val loggingInterceptor =  HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    //okhttp client
    private val httpClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    private fun getRetrofitClient() = Retrofit.Builder()
        .client(httpClient)
        .baseUrl("https://hn.algolia.com/api/")
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setFieldNamingPolicy(
                FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()))
        .build()


     // Build Api client
     val api = getRetrofitClient().create(NewsApi::class.java)

    interface NewsApi {

        @GET("v1/search")
        fun getSearchResult(@Query(value = "query") query: String?): Call<HackerNewsApi>
    }
}