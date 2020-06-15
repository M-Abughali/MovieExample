package com.mj.movieexample.network

import com.mj.movieexample.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class AuthInterceptor : Interceptor {

    private val API_KEY = "api_key"
    private val LANUAGE = "language"

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request
            .url
            .newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.API_TOKEN)
            .addQueryParameter(LANUAGE, Locale.getDefault().language)
            .build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }

}