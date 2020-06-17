package com.task.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mj.movieexample.data.Result
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.data.model.MovieResult
import io.reactivex.disposables.Disposable


abstract class BaseViewModel : ViewModel() {



    var disposable: Disposable? = null

    protected abstract fun onLoading(disposable: Disposable?)

    protected abstract fun onSuccess(`object`: MovieResult?)

    protected abstract fun onError(throwable: Throwable?)

    protected abstract fun getLiveData(): LiveData<Result<List<Movie>>>

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable?.dispose()
            disposable = null
        }
    }
}
