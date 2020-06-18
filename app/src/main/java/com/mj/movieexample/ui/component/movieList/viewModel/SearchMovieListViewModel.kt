package com.mj.movieexample.ui.component.movieList.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mj.movieexample.R
import com.mj.movieexample.data.Result
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.data.model.MovieResult
import com.mj.movieexample.data.remote.RemoteRepository
import com.mj.movieexample.network.NoInternetException
import com.mj.movieexample.network.RxSingleSchedulers
import com.mj.movieexample.util.Constants.INSTANCE.GENERAL_ERROR_MSG
import com.mj.movieexample.util.Constants.INSTANCE.SUCCESS_MSG
import com.task.ui.base.BaseViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SearchMovieListViewModel @Inject constructor(
    val repository: RemoteRepository, val rxSingleSchedulers: RxSingleSchedulers
) : BaseViewModel() {


    private var totalSearchMoviesData = MutableLiveData<ArrayList<Movie>>()
    private var searchMovieResultLiveDataPrivate = MutableLiveData<Result<List<Movie>>>()
    private var searchMoviePageLiveDataPrivate = MutableLiveData<Int>()

    private val searchMovieResultLiveData: LiveData<Result<List<Movie>>> get() = searchMovieResultLiveDataPrivate
    private val searchMoviePageLiveData: LiveData<Int> get() = searchMoviePageLiveDataPrivate
    private var previousKeyword: String = "";

    init {
        totalSearchMoviesData.value = ArrayList<Movie>()
        searchMoviePageLiveDataPrivate.value = 1
    }

    fun checkIfIsNewSearch(keyword: String) {
        if (!previousKeyword.equals(keyword)) {
            previousKeyword = keyword;
            searchMoviePageLiveDataPrivate.value = 1
            totalSearchMoviesData.value?.clear();
            searchMovieResultLiveDataPrivate.postValue(
                Result.Success(
                    null,
                    SUCCESS_MSG
                )
            )
        }
    }

    fun searchMovieFromServer(keyword: String) {
        checkIfIsNewSearch(keyword)
        disposable =
            repository.searchMovies(searchMoviePageLiveDataPrivate.value.toString(), keyword)
                .compose(rxSingleSchedulers.applySchedulers())
                .doOnSubscribe { o -> onLoading(o) }
                .subscribe(
                    { t -> onSuccess(t) },
                    { e -> onError(e) })
    }

    fun changeMoviePage() {
        val currentPage = searchMoviePageLiveDataPrivate.value!!
        searchMoviePageLiveDataPrivate.postValue(currentPage + 1)
    }


    override fun onLoading(disposable: Disposable?) {
        searchMovieResultLiveDataPrivate.postValue(Result.InProgrss)
    }

    override fun onSuccess(it: MovieResult?) {
        totalSearchMoviesData.value?.addAll(it!!.results)
        searchMovieResultLiveDataPrivate.postValue(
            Result.Success(
                it?.results,
                SUCCESS_MSG
            )
        )
        changeMoviePage()
    }

    override fun onError(throwable: Throwable?) {
        when (throwable) {
            is NoInternetException -> searchMovieResultLiveDataPrivate.postValue(Result.NetworkNoInternetError)
            else -> searchMovieResultLiveDataPrivate.postValue(
                Result.NetworkGeneralError(
                    GENERAL_ERROR_MSG
                )
            )
        }
    }

    private fun postAllDataIfStateChanged() {
        searchMovieResultLiveDataPrivate.postValue(
            Result.Success(
                totalSearchMoviesData.value,
                SUCCESS_MSG
            )
        )
    }

    public override fun getLiveData(): LiveData<Result<List<Movie>>> {
        postAllDataIfStateChanged()
        return searchMovieResultLiveData
    }

    fun getSearchPageLiveData(): LiveData<Int> {
        return searchMoviePageLiveData
    }
}