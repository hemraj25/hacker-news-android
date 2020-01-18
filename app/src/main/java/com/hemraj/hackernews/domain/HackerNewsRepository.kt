package com.hemraj.hackernews.domain

import com.hemraj.hackernews.Result
import kotlinx.coroutines.flow.Flow


interface HackerNewsRepository {

    suspend fun getSearchNewsList(searchKeyword: String?): Flow<Result<List<HackerNews>>>
}