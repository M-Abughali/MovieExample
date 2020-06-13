package com.mj.movieexample.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mj.movieexample.model.MovieRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(val movieRepository: MovieRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DisplayMovieViewModel::class.java)) {
            return DisplayMovieViewModel(repository = movieRepository) as T
        }
        throw IllegalArgumentException("error from factory")
    }

}