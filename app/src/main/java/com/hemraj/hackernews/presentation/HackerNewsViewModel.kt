package com.hemraj.hackernews.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hemraj.hackernews.domain.HackerNews
import com.hemraj.hackernews.domain.HackerNewsRepository
import com.hemraj.hackernews.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HackerNewsViewModel(private val repository: HackerNewsRepository): ViewModel() {

    var searchResult = MutableLiveData<List<HackerNews>>()

    fun getSearchResult(query: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getSearchNewsList(query)

            when(result) {
                is Result.Success -> {
                    searchResult.postValue(result.data.toDomain())
                }

                is Result.Error -> {
                    Log.d("Error", result.exception.message)
                }
            }
        }
    }
}