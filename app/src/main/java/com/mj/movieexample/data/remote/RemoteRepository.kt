package com.mj.movieexample.data.remote

import androidx.lifecycle.MutableLiveData
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.data.model.MovieResult
import com.mj.movieexample.network.NoInternetException
import com.mj.movieexample.util.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class RemoteRepository @Inject constructor(val movieApiServices: MovieApiServices) {
    private val mutableLiveData = MutableLiveData<Result<List<Movie>>>();


    fun getMoviesLiveData(): MutableLiveData<Result<List<Movie>>> {
        return mutableLiveData;
    }

    fun getAllMovies(page: String) {

        movieApiServices.getMovies(page).enqueue(object : Callback<MovieResult> {
            override fun onFailure(call: Call<MovieResult>, t: Throwable) {
                when (t) {
                    is NoInternetException -> mutableLiveData.postValue(Result.NetworkNoInternetError);
                    else -> {
                        mutableLiveData.postValue(Result.NetworkGeneralError("msg" + t.message));

                    }
                }
            }

            override fun onResponse(call: Call<MovieResult>, response: Response<MovieResult>) {

                mutableLiveData.postValue(Result.Success(response.body()?.results, "done"));

            }
        });


    }


}