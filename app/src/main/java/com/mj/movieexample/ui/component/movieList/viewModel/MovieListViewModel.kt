package com.mj.movieexample.ui.component.movieList.viewModel

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

class MovieListViewModel @Inject constructor(
    val repository: RemoteRepository, val rxSingleSchedulers: RxSingleSchedulers
) : BaseViewModel() {


    private var totalMoviesData = MutableLiveData<ArrayList<Movie>>();
    private var movieResultLiveData = MutableLiveData<Result<List<Movie>>>();
    private var moviePageLiveData = MutableLiveData<Int>();


    init {
        totalMoviesData.value = ArrayList<Movie>()
        moviePageLiveData.value = 1
    }


    fun getMovieFromServer() {
        disposable = repository.getMovies(moviePageLiveData.value.toString())
            .compose(rxSingleSchedulers.applySchedulers())
            .doOnSubscribe { o -> onLoading(o) }
            .subscribe(
                { t -> onSuccess(t) },
                { e -> onError(e) })
    }

    fun changeMoviePage() {
        val currentPage = moviePageLiveData.value!!;
        moviePageLiveData.postValue(currentPage + 1)
    }


    override fun onLoading(disposable: Disposable?) {
        movieResultLiveData.postValue(Result.InProgrss)
    }

    override fun onSuccess(it: MovieResult?) {
        totalMoviesData.value!!.addAll(it!!.results)
        movieResultLiveData.postValue(
            Result.Success(
                totalMoviesData.value,
                SUCCESS_MSG
            )
        )
        changeMoviePage()
    }

    override fun onError(throwable: Throwable?) {
        when (throwable) {
            is NoInternetException -> movieResultLiveData.postValue(Result.NetworkNoInternetError);
            else -> {
                movieResultLiveData.postValue(
                    Result.NetworkGeneralError(
                        GENERAL_ERROR_MSG
                    )
                );

            }
        }
    }

    public override fun getLiveData(): LiveData<Result<List<Movie>>> {
        return movieResultLiveData;
    }


}