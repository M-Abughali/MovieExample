package com.mj.movieexample.di.module

import android.app.Application
import com.mj.movieexample.model.MovieRepository
import com.mj.movieexample.network.MovieApiServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule() {
    @Singleton
    @Provides
    fun getRepostory(movieApiServices: MovieApiServices): MovieRepository {
        return MovieRepository(movieApiServices)
    }


}