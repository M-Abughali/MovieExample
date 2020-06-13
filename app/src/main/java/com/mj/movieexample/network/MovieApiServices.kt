package com.mj.movieexample.network

import com.mj.movieexample.BuildConfig.API_TOKEN
import com.mj.movieexample.model.MovieResult
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

interface MovieApiServices {
    //http://api.themoviedb.org/3/movie/now_playing?api_key=ab852022baac8256ea5738cd1b69fe1b
    @GET("movie/popular?api_key=$API_TOKEN")
     fun getMovies(
        @Query("language") language: String?,
        @Query("page") page: String?
    ): Call<MovieResult>

    @GET("search/movie?api_key=$API_TOKEN")
     fun searchMovies(
        @Query("language") language: String?,
        @Query("page") page: String?,
        @Query("query") query: String?
    ): Call<MovieResult>
}