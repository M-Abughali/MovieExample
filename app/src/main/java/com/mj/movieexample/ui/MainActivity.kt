package com.mj.movieexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mj.movieexample.core.MyApp
import com.mj.movieexample.R
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.databinding.ActivityMainBinding
import com.mj.movieexample.ui.base.BaseActivity
import com.mj.movieexample.ui.movieAdapter.MovieAdapter
import com.mj.movieexample.ui.viewModel.MovieListViewModel
import com.mj.movieexample.ui.viewModel.ViewModelFactory
import com.mj.movieexample.util.*;
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding;

    @Inject
    lateinit var viewModelFactory: ViewModelFactory;


    lateinit var movieViewModel: MovieListViewModel;


    override fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initToolBar() {
       // setUpIconVisibility(true)
        setTitle("get data activity")
        setSettingsIconVisibility(true)
        setRefreshVisibility(true)
    }

    override fun injectActivity(baseActivity: BaseActivity) {
        MyApp.getInstance().getMovieComponent().inject(this)
    }

    override fun initializeViewModel() {
        movieViewModel =
            ViewModelProvider(this, viewModelFactory).get(MovieListViewModel::class.java);

    }

    private fun handleMovieList(result: Result<List<Movie>>) {
        when (result) {
            is Result.Success -> {
                showLoadingProgrss(false)
                bindListData(result.data!!)
            }
            is Result.NetworkGeneralError -> {
                showLoadingProgrss(false)
                Toast.makeText(this, "faild " + result.msg, Toast.LENGTH_LONG).show();
            }
            is Result.InProgrss -> {
                showLoadingProgrss(true)
                Toast.makeText(this, "InProgrss", Toast.LENGTH_LONG).show();
            }
            is Result.NetworkNoInternetError -> {
                showLoadingProgrss(false)
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();

            }
        }

    }

    override fun observeViewModel() {
        observe(movieViewModel.getMovieLiveData(), ::handleMovieList)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        rvMovies.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        movieViewModel.getMovieFromServer();


    }


    private fun bindListData(list: List<Movie>) {
        if (!(list.isNullOrEmpty())) {
            rvMovies.adapter = MovieAdapter(list);
            showDataView(true)
        } else {
            showDataView(false)
        }
    }


    private fun showDataView(isVisible: Boolean) {
        // tvNoData.visibility = if (isVisible) View.GONE else View.VISIBLE
        rvMovies.visibility = if (isVisible) View.VISIBLE else View.GONE
    }


    private fun showLoadingProgrss(isVisible: Boolean) {
        progress.visibility = when (isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

}