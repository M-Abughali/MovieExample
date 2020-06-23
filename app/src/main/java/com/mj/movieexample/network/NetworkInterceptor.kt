package com.mj.movieexample.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.mj.movieexample.BuildConfig
import com.mj.movieexample.util.Constants.INSTANCE.NO_INTERNET_CONNECTION_MSG
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(val application: Application) : Interceptor {

    private val API_KEY = "api_key"
    private val LANGUAGE = "language"
    private val CONTENT_TYPE = "Content-Type"
    private val CONTENT_TYPE_VALUE = "application/json"

    private fun isInternetAvailable(): Boolean {
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
            .addQueryParameter(LANGUAGE, Locale.getDefault().language)
            .build()

        request = request.newBuilder().header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .method(request.method, request.body).url(url).build()


        if (!isInternetAvailable())
            throw  NoInternetException(NO_INTERNET_CONNECTION_MSG)

        return chain.proceed(request)

    }

}