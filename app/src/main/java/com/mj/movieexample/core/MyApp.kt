package com.mj.movieexample.core

import android.app.Application
import android.content.Context
import com.mj.movieexample.di.DaggerMovieComponent

import com.mj.movieexample.di.MovieComponent
import com.mj.movieexample.di.module.AppModule

class MyApp : Application() {
    private lateinit var movieComponent: MovieComponent;

     fun getMovieComponent(): MovieComponent {
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