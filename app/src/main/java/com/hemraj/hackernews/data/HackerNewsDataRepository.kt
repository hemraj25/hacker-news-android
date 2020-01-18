package com.hemraj.hackernews.data

import com.hemraj.hackernews.Result
import com.hemraj.hackernews.data.network.HackerNewsService
import com.hemraj.hackernews.domain.HackerNewsRepository
import kotlinx.coroutines.flow.flow


class HackerNewsDataRepository(private val api: HackerNewsService.NewsApi) : HackerNewsRepository {

    override suspend fun getSearchNewsList(searchKeyword: String?) =
        flow {
            try {
                val result = api.getSearchResult(searchKeyword).execute()
                if (result.isSuccessful)
                    emit(Result.success(result.body()!!.toDomain()))
                else
                    emit(Result.error(Exception("something went wrong")))
            } catch (e: Exception) {
                emit(Result.error(e))
            }
        }

}