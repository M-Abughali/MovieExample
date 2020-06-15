package com.mj.movieexample.core

import android.app.Application
import com.mj.movieexample.di.DaggerMovieComponent

import com.mj.movieexample.di.MovieComponent
import com.mj.movieexample.di.module.AppModule

class MyApp : Application() {
    private lateinit var movieComponent: MovieComponent;

    public fun getMovieComponent(): MovieComponent {
        return movieComponent;
    }

    companion object {
        private lateinit var Instance: MyApp;
        fun getInstance(): MyApp {
            return Instance;
        }
    }

    override fun onCreate() {
        super.onCreate()
        Instance = this;
        movieComponent = DaggerMovieComponent.builder().appModule(AppModule(this)).build();
    }
}