package com.mj.movieexample.ui.component.movieList.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mj.movieexample.data.Result

import com.mj.movieexample.data.model.MovieResult
import com.mj.movieexample.data.remote.RemoteRepository
import com.mj.movieexample.network.NoInternetException
import com.mj.movieexample.network.RxSingleSchedulers
import com.mj.movieexample.util.Constants
import com.mj.movieexample.util.LiveDataTestUtil
import io.reactivex.Single
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.StringContains
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.lang.Exception

class MovieListViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var remoteRepository: RemoteRepository
    private lateinit var viewModel: MovieListViewModel

    @Mock
    lateinit var observer: Observer<Result<Any>>


    @Before
    @Throws(java.lang.Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MovieListViewModel(remoteRepository, RxSingleSchedulers.TEST_SCHEDULER)
        viewModel.getLiveData().observeForever(observer)
    }

    @Test
    fun `check LiveData is NotNull`() {
        Mockito.`when`(remoteRepository.getMovies("1"))
            .thenReturn(null)
        Assert.assertNotNull(viewModel.getLiveData())
        Assert.assertTrue(viewModel.getLiveData().hasObservers())
    }

    @Test
    fun `Fetch Data from server with Success result`() {
        // arrange
        val returnedObject = MovieResult(
            page = 1,
            results = ArrayList(),
            total_pages = 10,
            total_results = 10
        )

        val expectedListView = returnedObject.results

        Mockito.`when`(remoteRepository.getMovies("1")).thenReturn(Single.just(returnedObject))

        //act
        viewModel.getMovieFromServer()

        // verify
        verify(observer).onChanged(Result.InProgress)
        val observedLiveDataListView =
            (LiveDataTestUtil.getValue(viewModel.getLiveData()) as Result.Success).data
        Assert.assertEquals(expectedListView, observedLiveDataListView)
    }

    @Test
    fun `Fetch data with No Internet Connection exception happened`() {
        // arrange
        Mockito.`when`(remoteRepository.getMovies("1"))
            .thenReturn(Single.error(NoInternetException(Constants.NO_INTERNET_CONNECTION_MSG)))

        // act
        try {
            viewModel.getMovieFromServer()
        } catch (e: NoInternetException) {

            //verify
            assertThat(e.message, StringContains(Constants.NO_INTERNET_CONNECTION_MSG))
        }

    }

    @Test
    fun `Fetch data with general exception happened`() {
        // arrange
        Mockito.`when`(remoteRepository.getMovies("1"))
            .thenReturn(Single.error(Exception(Constants.GENERAL_ERROR_MSG)))
        try {

            // act
            viewModel.getMovieFromServer()
        } catch (e: Exception) {
            //verify
            assertThat(e.message, StringContains(Constants.GENERAL_ERROR_MSG))
        }

    }


    @Test
    fun `Check live data pagination`() {

        // arrange
        val expectedResult=2

        //act
        viewModel.changeMoviePage()

        //verify
        val returnedValue = LiveDataTestUtil.getValue(viewModel.getPageLiveData())
        Assert.assertEquals(expectedResult, returnedValue)
    }


}