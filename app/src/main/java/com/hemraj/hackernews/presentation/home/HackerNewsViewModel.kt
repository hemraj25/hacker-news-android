package com.hemraj.hackernews.presentation.home

import androidx.lifecycle.*
import com.hemraj.hackernews.Result
import com.hemraj.hackernews.data.HackerNewsDataRepository
import com.hemraj.hackernews.domain.HackerNews
import com.hemraj.hackernews.util.AppDispatchers
import com.hemraj.hackernews.util.log
import kotlinx.coroutines.delay

class HackerNewsViewModel(
    private val appDispatchers: AppDispatchers,
    private val repository: HackerNewsDataRepository): ViewModel() {

    private val TAG = HackerNewsViewModel::class.java.canonicalName

    private var searchQuery = MutableLiveData<String>()

    fun getSearchResult(query: String) {
        searchQuery.value = query
    }

    val searchResultLiveData: LiveData<Result<List<HackerNews>>> = searchQuery.switchMap {
        liveData(appDispatchers.IO) {
            try {
                emit(Result.loading())
                delay(1000)
                log(TAG, "Search Query: ${searchQuery.value}")
                repository.getSearchNewsList(it).collect {
                    emit(it)
                }
            } catch (ioException: Exception) {
                emit(Result.error(ioException))
                log("Error", ioException.message?:"")
            }
        }
    }
}