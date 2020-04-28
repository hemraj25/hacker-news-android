package com.hemraj.hackernews.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hemraj.hackernews.Result
import com.hemraj.hackernews.data.network.NewsNetworkApi
import com.hemraj.hackernews.domain.HackerNews
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import getSearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Answers
import org.mockito.MockitoAnnotations
import retrofit2.Response

class HackerNewsDataRepositoryTest {

    @Rule
    @JvmField
    val rule2 = InstantTaskExecutorRule()

    private lateinit var repository: HackerNewsDataRepository

    private val networkApi = mock<NewsNetworkApi>(defaultAnswer = Answers.RETURNS_DEEP_STUBS)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = HackerNewsDataRepository(networkApi)
    }

    @Test
    fun getSearchNewsListSuccess() = runBlocking {
        val retroResponseMock = mock<Response<HackerNewsApi>>()
        whenever(networkApi.getSearchResult(any()).execute()).thenReturn(retroResponseMock)
        val response = networkApi.getSearchResult("sports").execute()
        whenever(response.isSuccessful).thenReturn(true)
        whenever(response.body()).thenReturn(getSearchResult())
        val flow: Flow<Result<List<HackerNews>>> = repository.getSearchNewsList("sports")
        flow.collect {
            assert(it.status == Result.SUCCESS)
        }
    }

    @Test
    fun getSearchNewsListFailure() = runBlocking {
        val retroResponseMock = mock<Response<HackerNewsApi>>()
        whenever(networkApi.getSearchResult(any()).execute()).thenReturn(retroResponseMock)
        val response = networkApi.getSearchResult("sports").execute()
        whenever(response.isSuccessful).thenReturn(false)
        val flow: Flow<Result<List<HackerNews>>> = repository.getSearchNewsList("sports")
        flow.collect {
            assert(it.status == Result.ERROR)
        }
    }
}