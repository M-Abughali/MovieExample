package com.mj.movieexample.model

import androidx.lifecycle.MutableLiveData
import com.mj.movieexample.network.MovieApiServices
import com.mj.movieexample.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class MovieRepository @Inject constructor(val movieApiServices: MovieApiServices) {
    private val mutableLiveData = MutableLiveData<Result<List<Movie>>>();



    fun getMoviesLiveData(): MutableLiveData<Result<List<Movie>>> {
        return mutableLiveData;
    }

    fun getAllMovies(): MutableLiveData<Result<List<Movie>>> {
        movieApiServices.getMovies("en", "1").enqueue(object : Callback<MovieResult> {
            override fun onFailure(call: Call<MovieResult>, t: Throwable) {
                mutableLiveData.postValue(Result.Fail("msg" + t.message));
            }

            override fun onResponse(call: Call<MovieResult>, response: Response<MovieResult>) {
                mutableLiveData.postValue(Result.Success(response.body()?.results, "done"));

            }
        });

        return mutableLiveData

    }


}