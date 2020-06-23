package com.mj.movieexample.network

import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface RxSingleSchedulers {
    fun <T> applySchedulers(): SingleTransformer<T, T>?

    companion object {
        val TEST_SCHEDULER: RxSingleSchedulers = object : RxSingleSchedulers {
            override fun <T> applySchedulers(): SingleTransformer<T, T> {
                return SingleTransformer { single: Single<T> ->
                    single
                        .subscribeOn(Schedulers.trampoline())
                        .observeOn(Schedulers.trampoline())
                }
            }
        }
        val DEFAULT: RxSingleSchedulers = object : RxSingleSchedulers {
            override fun <T> applySchedulers(): SingleTransformer<T, T> {
                return SingleTransformer { single: Single<T> ->
                    single
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                }
            }
        }
    }
}
