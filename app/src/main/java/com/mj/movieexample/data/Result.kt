package com.mj.movieexample.data

sealed class Result<out T : Any> {


    class Success<out T : Any>(val data: T?, val msg: String) : Result<T>()
    class NetworkGeneralError(val msg: String) : Result<Nothing>()
    object NetworkNoInternetError : Result<Nothing>()
    object InProgress : Result<Nothing>()

}