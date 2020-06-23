package com.mj.movieexample.network

import com.mj.movieexample.BuildConfig.BASE_URL
import com.mj.movieexample.core.MyApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ServiceGenerator @Inject
constructor() {

    //Network constants
    private val TIMEOUT_CONNECT = 30   //In seconds
    private val TIMEOUT_READ = 30   //In seconds
    private val TIMEOUT_WRITE = 30   //In seconds

    private val retrofit: Retrofit

    private val okHttpBuilder =
        OkHttpClient.Builder().addInterceptor(NetworkInterceptor(MyApp.getInstance()))


    init {
        okHttpBuilder.connectTimeout(TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(TIMEOUT_WRITE.toLong(), TimeUnit.SECONDS)

        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}