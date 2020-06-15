package com.mj.movieexample.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.mj.movieexample.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(val application: Application) : Interceptor {

    private val API_KEY = "api_key"
    private val LANUAGE = "language"

    fun isInternetAvailable(): Boolean {
        val info = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        info.activeNetworkInfo.also {

            return it != null && it.isConnected
        }
    }


    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val url = request
            .url
            .newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.API_TOKEN)
            .addQueryParameter(LANUAGE, Locale.getDefault().language)
            .build()
        request = request.newBuilder().url(url).build()


        if (!isInternetAvailable())
            throw  NoInternetException("noInternt")



        return chain.proceed(request)

    }

}