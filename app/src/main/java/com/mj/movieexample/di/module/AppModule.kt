package com.mj.movieexample.di.module

import android.app.Application
import com.mj.movieexample.BuildConfig
import com.mj.movieexample.network.MovieApiServices
import com.mj.movieexample.network.NetworkInterceptor
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class AppModule(val application: Application) {
    @Provides
    fun provideApplication(): Application {
        return application;
    }


}