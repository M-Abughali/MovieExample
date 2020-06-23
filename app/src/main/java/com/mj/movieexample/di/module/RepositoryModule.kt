package com.mj.movieexample.di.module

import com.mj.movieexample.data.remote.RemoteRepository
import com.mj.movieexample.data.remote.MovieApiServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun getRepository(movieApiServices: MovieApiServices): RemoteRepository {
        return RemoteRepository(movieApiServices)
    }


}