package com.hemraj.hackernews.data

import com.hemraj.hackernews.data.network.HackerNewsService
import com.hemraj.hackernews.domain.HackerNewsRepository
import com.hemraj.hackernews.Result


class HackerNewsDataRepository(private val api: HackerNewsService.NewsApi): HackerNewsRepository {

    override suspend fun getSearchNewsList(searchKeyword: String?): Result<HackerNewsApi> {
        return try {
            val result = api.getSearchResult(searchKeyword).execute()
            if (result.isSuccessful)
                 Result.Success(result.body()!!)
            else
                 Result.Error(Exception("something went wrong"))
        } catch (e: Exception) {
           return Result.Error(Exception("something went wrong"))
        }
    }

}