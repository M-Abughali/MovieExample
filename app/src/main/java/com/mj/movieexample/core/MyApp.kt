package com.mj.movieexample.core

import android.app.Application
import com.mj.movieexample.di.DaggerMovieComponent
import com.mj.movieexample.di.MovieComponent

class MyApp : Application() {
    private lateinit var movieComponent: MovieComponent

    fun getMovieComponent(): MovieComponent {
        return movieComponent
    }

    companion object {
        private lateinit var Instance: MyApp

        fun getInstance(): MyApp {
            return Instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        Instance = this
        movieComponent = DaggerMovieComponent.builder().injectApplication(this).build()

    }


}