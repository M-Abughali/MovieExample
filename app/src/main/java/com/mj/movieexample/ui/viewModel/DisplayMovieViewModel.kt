package com.mj.movieexample.ui.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import com.mj.movieexample.model.Movie
import com.mj.movieexample.model.MovieRepository
import com.mj.movieexample.util.Result
import kotlinx.coroutines.delay
import javax.inject.Inject

class DisplayMovieViewModel @Inject constructor(val repository: MovieRepository) :
    ViewModel() {
    private var movieLiveData: MutableLiveData<Result<List<Movie>>>;

    init {
        movieLiveData = repository.getMoviesLiveData();
    }

    suspend fun getMovieFromServer() {
        movieLiveData.postValue(Result.InProgrss);
        delay(3000)
        movieLiveData.postValue(repository.getAllMovies().value)
    }

    fun getMovieLiveData(): MutableLiveData<Result<List<Movie>>> {
        return movieLiveData;
    }
}