package com.mj.movieexample.ui.component.movieList.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.data.remote.RemoteRepository
import com.mj.movieexample.data.Result
import com.task.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(val repository: RemoteRepository) : BaseViewModel() {
    private lateinit var repositorymovieResultLiveData: MutableLiveData<Result<List<Movie>>>;
    private var totalMoviesData = MutableLiveData<ArrayList<Movie>>();


    private var movieResultLiveData = MutableLiveData<Result<List<Movie>>>();
    private var moviePageLiveData = MutableLiveData<Int>();

    init {
        initLiveData()
        observeTempLiveData()
    }

    fun initLiveData() {
        repositorymovieResultLiveData = repository.getMoviesLiveData();
        totalMoviesData.postValue(ArrayList<Movie>())
        moviePageLiveData.postValue(1);
    }

    fun observeTempLiveData() {
        repositorymovieResultLiveData.observeForever(Observer {

            when (it) {
                is Result.Success -> {
                    totalMoviesData.value!!.addAll(it.data!!)
                    movieResultLiveData.postValue(Result.Success(totalMoviesData.value, "done"))
                    changeMoviePage()
                }
                else -> {
                    movieResultLiveData.postValue(repositorymovieResultLiveData.value)

                }

            }

        });

    }

    fun getMovieFromServer() {
        viewModelScope.launch {
            movieResultLiveData.postValue(Result.InProgrss);
            repository.getAllMovies(moviePageLiveData.value.toString());
        }
    }

    fun changeMoviePage() {
        val currentPage = moviePageLiveData.value!!;
        moviePageLiveData.postValue(currentPage + 1)
    }

    fun getMovieResultLiveData(): MutableLiveData<Result<List<Movie>>> {
        return movieResultLiveData;
    }


}