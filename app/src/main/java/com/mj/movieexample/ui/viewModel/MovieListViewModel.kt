package com.mj.movieexample.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.data.remote.RemoteRepository
import com.mj.movieexample.util.Result
import com.task.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(val repository: RemoteRepository) : BaseViewModel() {
    private var movieLiveData: MutableLiveData<Result<List<Movie>>>;

    init {
        movieLiveData = repository.getMoviesLiveData();
    }

    fun getMovieFromServer() {
        CoroutineScope(Dispatchers.IO).launch {
            movieLiveData.postValue(Result.InProgrss);
            delay(3000)
            movieLiveData.postValue(repository.getAllMovies().value)
        }
    }

    fun getMovieLiveData(): MutableLiveData<Result<List<Movie>>> {
        return movieLiveData;
    }
}