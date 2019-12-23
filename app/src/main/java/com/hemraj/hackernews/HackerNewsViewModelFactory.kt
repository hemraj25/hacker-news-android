package com.hemraj.hackernews.hackernews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hemraj.hackernews.domain.HackerNewsRepository
import com.hemraj.hackernews.presentation.HackerNewsViewModel


class HackerNewsViewModelFactory(private val repository: HackerNewsRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HackerNewsViewModel::class.java) ->
                HackerNewsViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}