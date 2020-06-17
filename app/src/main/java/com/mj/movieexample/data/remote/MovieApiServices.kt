package com.mj.movieexample.data.remote

import com.mj.movieexample.data.model.MovieResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApiServices {
    //http://api.themoviedb.org/3/movie/now_playing?api_key=ab852022baac8256ea5738cd1b69fe1b
    @GET("movie/popular")
    fun getMovies(
        @Query("page") page: String?
    ): Single<MovieResult>

    @GET("search/movie")
     fun searchMovies(
        @Query("page") page: String?,
        @Query("query") query: String?
    ): Single<MovieResult>
}