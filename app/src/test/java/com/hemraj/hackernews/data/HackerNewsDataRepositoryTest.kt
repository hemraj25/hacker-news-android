package com.hemraj.hackernews.data

import MainCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hemraj.hackernews.Result
import com.hemraj.hackernews.data.network.NewsNetworkApi
import com.hemraj.hackernews.domain.HackerNews
import getSearchResult
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import java.io.IOException

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class HackerNewsDataRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var repository: HackerNewsDataRepository

    @MockK
    lateinit var networkApi: NewsNetworkApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = HackerNewsDataRepository(networkApi)
    }

    @Test
    fun getSearchNewsListSuccess() = mainCoroutineRule.runTest {
        val retroResponseMock = mockk<Response<HackerNewsApi>>()
        every {  networkApi.getSearchResult(any()).execute() } returns retroResponseMock
        val response = networkApi.getSearchResult("sports").execute()
        every { response.isSuccessful } returns true
        every { response.body() } returns getSearchResult()
        val flow: Flow<Result<List<HackerNews>>> = repository.getSearchNewsList("sports")
        flow.collect {
            assert(it.status == Result.SUCCESS)
            assert(it.data?.isNotEmpty() == true)
        }
    }

    @Test
    fun getSearchNewsListFailure() = mainCoroutineRule.runTest {
        val retroResponseMock = mockk<Response<HackerNewsApi>>()
        every {  networkApi.getSearchResult(any()).execute() } returns retroResponseMock
        val response = networkApi.getSearchResult("sports").execute()
        every { response.isSuccessful } returns false
        val flow: Flow<Result<List<HackerNews>>> = repository.getSearchNewsList("sports")
        flow.collect {
            assert(it.status == Result.ERROR)
        }
    }

    @Test(expected = IOException::class)
    fun getSearchNewsListException() = mainCoroutineRule.runTest {
        every {  networkApi.getSearchResult(any()).execute() } returns throwException()
        val flow: Flow<Result<List<HackerNews>>> = repository.getSearchNewsList("sports")
        flow.collect {
            assert(it.status == Result.ERROR)
            assert(it.error?.message == "Job cancelled")
        }
    }

    private fun throwException(): Response<HackerNewsApi> {
        throw IOException("Job cancelled")
    }
}