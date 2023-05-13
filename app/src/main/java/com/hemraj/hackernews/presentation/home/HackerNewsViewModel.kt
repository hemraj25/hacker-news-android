package com.hemraj.hackernews.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.hemraj.hackernews.Result
import com.hemraj.hackernews.data.HackerNewsDataRepository
import com.hemraj.hackernews.domain.HackerNews
import com.hemraj.hackernews.util.AppDispatchers
import com.hemraj.hackernews.util.log
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HackerNewsViewModel(
    private val appDispatchers: AppDispatchers,
    private val repository: HackerNewsDataRepository
) : ViewModel() {

    private val TAG = HackerNewsViewModel::class.java.canonicalName
    private var searchQuery = MutableStateFlow("")
    val searchResultLiveData = MutableLiveData<Result<List<HackerNews>>>()
    var newsList = listOf<HackerNews>()
    var showTypingAnimation = mutableStateOf(true)

    fun getSearchResult(query: String) {
        log(TAG, "Typed Query: $query")
        searchQuery.value = query
    }

    @OptIn(FlowPreview::class)
    fun observeSearchQueryInput() {
        viewModelScope.launch {
            searchQuery.debounce(500).collect { query ->
                if (query.length > 2) {
                    getNewsList(query)
                } else {
                    showTypingAnimation.value = true
                }
            }
        }
    }

    private suspend fun getNewsList(searchQuery: String) {
        try {
            searchResultLiveData.value = Result.loading()
            log(TAG, "Search Query: $searchQuery")
            repository.getSearchNewsList(searchQuery)
                .flowOn(appDispatchers.IO)
                .collect {
                    searchResultLiveData.value = it
                }
        } catch (ioException: Exception) {
            searchResultLiveData.value = Result.error(ioException)
            log("Error", ioException.message ?: "")
        }
    }
}