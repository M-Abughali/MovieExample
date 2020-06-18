package com.mj.movieexample.data.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mj.movieexample.R
import com.mj.movieexample.core.MyApp
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.data.model.MovieResult
import com.mj.movieexample.network.NoInternetException
import com.mj.movieexample.data.Result
import io.reactivex.Observer
import io.reactivex.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class RemoteRepository @Inject constructor(val movieApiServices: MovieApiServices) {


    fun getMovies(page: String): Single<MovieResult> {
        return movieApiServices.getMovies("" + page);
    }

    fun searchMovies(page: String,keyword:String): Single<MovieResult> {
        return movieApiServices.searchMovies("" + page,keyword);
    }

}