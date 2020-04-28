package com.hemraj.hackernews.data.network

import com.hemraj.hackernews.data.HackerNewsApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsNetworkApi {
    @GET("v1/search")
    fun getSearchResult(@Query(value = "query") query: String?): Call<HackerNewsApi>
}