package com.mj.movieexample.util

sealed class Result<out T : Any> {
    class Success<out T : Any>(val data: T?, val msg: String) : Result<T>()
    class Fail(val msg: String) : Result<Nothing>()
    object InProgrss : Result<Nothing>()

}