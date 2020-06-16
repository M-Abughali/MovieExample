package com.mj.movieexample.di.module

import com.mj.movieexample.data.remote.MovieApiServices
import com.mj.movieexample.network.ServiceGenerator
import dagger.Module
import dagger.Provides

@Module
class RetrofitServiceModule {
    @Provides
    fun getRetrofitService(retrofitGenerator: ServiceGenerator): MovieApiServices {
        return retrofitGenerator.createService(MovieApiServices::class.java);
    }

}