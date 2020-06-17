package com.mj.movieexample.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.mj.movieexample.data.Result
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.data.model.MovieResult
import com.mj.movieexample.network.NoInternetException
import com.mj.movieexample.network.RxSingleSchedulers
import com.mj.movieexample.ui.component.movieList.viewModel.MovieListViewModel
import com.mj.movieexample.util.Constants
import com.util.LiveDataTestUtil
import io.reactivex.Single
import org.hamcrest.core.StringContains
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class RemoteRepositoryTest2 {
//    @Rule
//    @JvmField
//    val rule = InstantTaskExecutorRule()
//
//    @Mock
//    lateinit var apiClient: RemoteRepository
//    lateinit var viewModel: MovieListViewModel
//
//    @Mock
//    lateinit var observer: Observer<Result<*>>
//
//
//    @Before
//    @Throws(java.lang.Exception::class)
//    fun setUp() {
//        MockitoAnnotations.initMocks(this)
//        viewModel = MovieListViewModel(apiClient, RxSingleSchedulers.TEST_SCHEDULER)
//        viewModel.getLiveData().observeForever(observer)
//    }
//
//    @Test
//    fun testLiveDataNotNull() {
//        Mockito.`when`(apiClient.getMovies("1"))
//            .thenReturn(null)
//        Assert.assertNotNull(viewModel.getLiveData())
//        Assert.assertTrue(viewModel.getLiveData().hasObservers())
//    }
//
//    @Test
//    fun testApiFetchDataSuccess() {
//        // arrange
//        val returentObject = MovieResult(
//            page = 1,
//            results = ArrayList<Movie>(),
//            total_pages = 10,
//            total_results = 10
//        );
//
//        val expectedListView = returentObject.results;
//
//        Mockito.`when`(apiClient.getMovies("1")).thenReturn(Single.just(returentObject))
//
//        //act
//        viewModel.getMovieFromServer()
//
//        // verfiy
//        verify(observer).onChanged(Result.InProgrss);
//        val observedLiveDataListView =
//            (LiveDataTestUtil.getValue(viewModel.getLiveData()) as Result.Success).data;
//        Assert.assertEquals(expectedListView, observedLiveDataListView);
//    }
//
//    @Test
//    fun testNoInternetConnectionError() {
//        // arrange
//        Mockito.`when`(apiClient.getMovies("1"))
//            .then { throw NoInternetException(Constants.NO_INTERNET_CONNECTION_MSG) }
//        try {
//            viewModel.getMovieFromServer()
//        } catch (e: NoInternetException) {
//            assertThat(e.message, StringContains(Constants.NO_INTERNET_CONNECTION_MSG))
//        }
//
//    }
}