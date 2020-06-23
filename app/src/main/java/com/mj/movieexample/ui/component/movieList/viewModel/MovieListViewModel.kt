package com.mj.movieexample.ui.component.movieList.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mj.movieexample.data.Result
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.data.model.MovieResult
import com.mj.movieexample.data.remote.RemoteRepository
import com.mj.movieexample.network.NoInternetException
import com.mj.movieexample.network.RxSingleSchedulers
import com.mj.movieexample.ui.base.BaseViewModel
import com.mj.movieexample.util.Constants.INSTANCE.GENERAL_ERROR_MSG
import com.mj.movieexample.util.Constants.INSTANCE.SUCCESS_MSG
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MovieListViewModel @Inject constructor(
    private val repository: RemoteRepository,private  val rxSingleSchedulers: RxSingleSchedulers
) : BaseViewModel() {


    private var totalMoviesData = MutableLiveData<ArrayList<Movie>>()
    private var movieResultLiveDataPrivate = MutableLiveData<Result<List<Movie>>>()
    private var moviePageLiveDataPrivate = MutableLiveData<Int>()

    private val movieResultLiveData: LiveData<Result<List<Movie>>> get() = movieResultLiveDataPrivate
    private val moviePageLiveData: LiveData<Int> get() = moviePageLiveDataPrivate


    init {
        totalMoviesData.value = ArrayList()
        moviePageLiveDataPrivate.value = 1
    }


       @Inject
    fun getMovieFromServer() {
        disposable = repository.getMovies(moviePageLiveDataPrivate.value.toString())
            .compose(rxSingleSchedulers.applySchedulers())
            .doOnSubscribe { o -> onLoading(o) }
            .subscribe(
                { t -> onSuccess(t) },
                { e -> onError(e) })
    }

    fun changeMoviePage() {
        val currentPage = moviePageLiveDataPrivate.value!!
        moviePageLiveDataPrivate.postValue(currentPage + 1)
    }


    override fun onLoading(disposable: Disposable?) {
        movieResultLiveDataPrivate.postValue(Result.InProgress)
    }

    override fun onSuccess(it: MovieResult?) {
        totalMoviesData.value?.addAll(it!!.results)
        movieResultLiveDataPrivate.postValue(
            Result.Success(
                it?.results,
                SUCCESS_MSG
            )
        )
        changeMoviePage()
    }

    override fun onError(throwable: Throwable?) {
        when (throwable) {
            is NoInternetException -> movieResultLiveDataPrivate.postValue(Result.NetworkNoInternetError)
            else -> movieResultLiveDataPrivate.postValue(
                Result.NetworkGeneralError(
                    GENERAL_ERROR_MSG
                )
            )
        }
    }

    private fun postAllDataIfStateChanged(){
        movieResultLiveDataPrivate.postValue(
            Result.Success(
                totalMoviesData.value,
                SUCCESS_MSG
            )
        )
    }
    public override fun getLiveData(): LiveData<Result<List<Movie>>> {
        postAllDataIfStateChanged()
        return movieResultLiveData
    }

    fun getPageLiveData(): LiveData<Int> {


        return moviePageLiveData
    }
}