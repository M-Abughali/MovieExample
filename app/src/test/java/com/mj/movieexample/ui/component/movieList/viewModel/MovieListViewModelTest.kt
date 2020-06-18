package com.mj.movieexample.ui.component.movieList.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mj.movieexample.data.Result
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.data.model.MovieResult
import com.mj.movieexample.data.remote.RemoteRepository
import com.mj.movieexample.network.NoInternetException
import com.mj.movieexample.network.RxSingleSchedulers
import com.mj.movieexample.util.Constants
import com.util.LiveDataTestUtil
import io.reactivex.Observable
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
import java.lang.IllegalStateException

class MovieListViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var remoteRepository: RemoteRepository
    lateinit var viewModel: MovieListViewModel

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
        val returentObject = MovieResult(
            page = 1,
            results = ArrayList<Movie>(),
            total_pages = 10,
            total_results = 10
        )

        val expectedListView = returentObject.results

        Mockito.`when`(remoteRepository.getMovies("1")).thenReturn(Single.just(returentObject))

        //act
        viewModel.getMovieFromServer()

        // verfiy
        verify(observer).onChanged(Result.InProgrss)
        val observedLiveDataListView =
            (LiveDataTestUtil.getValue(viewModel.getLiveData()) as Result.Success).data
        Assert.assertEquals(expectedListView, observedLiveDataListView)
    }

    @Test
    fun `Fetch data with No Internet Connection exception happend`() {
        // arrange
        Mockito.`when`(remoteRepository.getMovies("1"))
            .thenReturn(Single.error(NoInternetException(Constants.NO_INTERNET_CONNECTION_MSG)))

        // act
        try {
            viewModel.getMovieFromServer()
        } catch (e: NoInternetException) {

            //verfiy
            assertThat(e.message, StringContains(Constants.NO_INTERNET_CONNECTION_MSG))
        }

    }

    @Test
    fun `Fetch data with general exception happend`() {
        // arrange
        Mockito.`when`(remoteRepository.getMovies("1"))
            .thenReturn(Single.error(Exception(Constants.GENERAL_ERROR_MSG)))
        try {

            // act
            viewModel.getMovieFromServer()
        } catch (e: Exception) {
            //verfiy
            assertThat(e.message, StringContains(Constants.GENERAL_ERROR_MSG))
        }

    }


    @Test
    fun `Check live data pagination`() {

        // arrange
        val expectedResult=2

        //act
        viewModel.changeMoviePage()

        //verfiy
        val returnedValue = LiveDataTestUtil.getValue(viewModel.getPageLiveData())
        Assert.assertEquals(expectedResult, returnedValue)
    }


}