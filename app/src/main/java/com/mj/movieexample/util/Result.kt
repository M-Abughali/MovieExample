package com.mj.movieexample.util

sealed class Result<out T : Any> {

    object InProgrss : Result<Nothing>()
    class Success<out T : Any>(val data: T?, val msg: String) : Result<T>()
    class NetworkGeneralError(val msg: String) : Result<Nothing>()
    object NetworkNoInternetError : Result<Nothing>()

}