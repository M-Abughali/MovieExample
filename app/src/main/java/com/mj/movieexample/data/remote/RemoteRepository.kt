package com.mj.movieexample.data.remote

import com.mj.movieexample.data.model.MovieResult
import io.reactivex.Single
import javax.inject.Inject


class RemoteRepository @Inject constructor(private val movieApiServices: MovieApiServices) {


    fun getMovies(page: String): Single<MovieResult> {
        return movieApiServices.getMovies("" + page)
    }

    fun searchMovies(page: String,keyword:String): Single<MovieResult> {
        return movieApiServices.searchMovies("" + page,keyword)
    }

}