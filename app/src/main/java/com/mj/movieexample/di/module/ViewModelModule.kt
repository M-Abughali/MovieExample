package com.mj.movieexample.di.module
import androidx.lifecycle.ViewModel
import com.mj.movieexample.ui.viewModel.MovieListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    internal abstract fun bindMovieListViewModel(viewModel: MovieListViewModel): ViewModel
}
