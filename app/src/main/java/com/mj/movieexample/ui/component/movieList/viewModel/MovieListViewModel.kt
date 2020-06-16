package com.mj.movieexample.ui.component.movieList.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.data.remote.RemoteRepository
import com.mj.movieexample.util.Result
import com.task.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(val repository: RemoteRepository) : BaseViewModel() {
    private var movieLiveData: MutableLiveData<Result<List<Movie>>>;

    init {
        movieLiveData = repository.getMoviesLiveData();
    }

    fun getMovieFromServer() {
        Log.e("get from server","getMovieFromServer");
        CoroutineScope(Dispatchers.IO).launch {
            movieLiveData.postValue(Result.InProgrss);
           // delay(10000)
            movieLiveData.postValue(repository.getAllMovies().value)
        }
    }

    fun getMovieLiveData(): MutableLiveData<Result<List<Movie>>> {
        return movieLiveData;
    }
}