package com.hemraj.hackernews.domain

import com.hemraj.hackernews.data.HackerNewsApi
import com.hemraj.hackernews.Result


interface HackerNewsRepository {

    suspend fun getSearchNewsList(searchKeyword: String?): Result<HackerNewsApi>
}