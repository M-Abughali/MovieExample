package com.mj.movieexample.di.module

import com.mj.movieexample.BuildConfig.BASE_URL
import com.mj.movieexample.network.MovieApiServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule {


    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Provides
    fun getRetrofitService(retrofit: Retrofit): MovieApiServices {
        return retrofit.create(MovieApiServices::class.java);
    }

}