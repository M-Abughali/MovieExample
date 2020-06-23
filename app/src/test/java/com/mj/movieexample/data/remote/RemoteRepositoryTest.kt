package com.mj.movieexample.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mj.movieexample.data.model.MovieResult
import com.mj.movieexample.util.Constants
import io.reactivex.Single
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.junit.Rule
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.lang.Exception


@RunWith(JUnit4::class)
class RemoteRepositoryTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var remoteRepository: RemoteRepository

    @Before
    @Throws(java.lang.Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `check RemoteRepository is NotNull`() {
        assertNotNull(remoteRepository)
    }

    @Test
    fun `Fetch Data from retrofit api with Success result`() {
        // arrange
        val returnedObject = MovieResult(
            page = 1,
            results = ArrayList(),
            total_pages = 10,
            total_results = 10
        )

        val expectedListView = Single.just(returnedObject)

        `when`(remoteRepository.getMovies("1")).thenReturn(expectedListView)

        //act
        val returned: Single<MovieResult> = remoteRepository.getMovies("1")

        // verify
        assertEquals(expectedListView, returned)
    }


    @Test
    fun `Fetch Data from retrofit api with error result`() {
        // arrange
        val expectedResult = Single.error<MovieResult>(Exception(Constants.GENERAL_ERROR_MSG))
        `when`(remoteRepository.getMovies("1")).thenReturn(expectedResult)

        //act
        val returned: Single<MovieResult> = remoteRepository.getMovies("1")

        // verify
        assertEquals(expectedResult, returned)
    }

}