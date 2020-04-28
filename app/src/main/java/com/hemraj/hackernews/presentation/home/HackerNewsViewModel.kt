package com.hemraj.hackernews.presentation.home

import android.util.Log
import androidx.lifecycle.*
import com.hemraj.hackernews.Result
import com.hemraj.hackernews.data.HackerNewsDataRepository
import com.hemraj.hackernews.domain.HackerNews
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

class HackerNewsViewModel(private val repository: HackerNewsDataRepository): ViewModel() {

    private val TAG = HackerNewsViewModel::class.java.canonicalName

    private var searchQuery = MutableLiveData<String>()

    fun getSearchResult(query: String) {
        searchQuery.value = query
    }

    val searchResultLiveData: LiveData<Result<List<HackerNews>>> = searchQuery.switchMap {
        liveData(Dispatchers.IO) {
            try {
                emit(Result.loading())
                delay(1000)
                Log.d(TAG, "Search Query: ${searchQuery.value}")
                repository.getSearchNewsList(it).collect {
                    emit(it)
                }
            } catch (ioException: Exception) {
                emit(Result.error(ioException))
                Log.d("Error", ioException.message?:"")
            }
        }
    }
}