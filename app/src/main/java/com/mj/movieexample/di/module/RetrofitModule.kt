package com.mj.movieexample.di.module

import com.mj.movieexample.BuildConfig.BASE_URL
import com.mj.movieexample.core.MyApp
import com.mj.movieexample.network.MovieApiServices
import com.mj.movieexample.network.NetworkInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class RetrofitModule {
    @Provides
    fun getRetrofit(): Retrofit {
        val OkHttpClient =
            OkHttpClient.Builder().addInterceptor(NetworkInterceptor(MyApp.getInstance()))
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES).build();

        return Retrofit.Builder().baseUrl(BASE_URL).client(OkHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Provides
    fun getRetrofitService(retrofit: Retrofit): MovieApiServices {
        return retrofit.create(MovieApiServices::class.java);
    }

}