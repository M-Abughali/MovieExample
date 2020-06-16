package com.mj.movieexample.ui.component.movieList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mj.movieexample.core.MyApp
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.databinding.ActivityMovieListBinding
import com.mj.movieexample.ui.base.BaseActivity
import com.mj.movieexample.ui.component.movieList.movieAdapter.MovieAdapter
import com.mj.movieexample.ui.component.movieList.viewModel.MovieListViewModel
import com.mj.movieexample.ui.base.ViewModelFactory
import com.mj.movieexample.ui.base.listeners.RecyclerItemListener
import com.mj.movieexample.ui.component.movieDetails.MovieDetailsActivity
import com.mj.movieexample.util.*;
import kotlinx.android.synthetic.main.activity_movie_list.*
import javax.inject.Inject

class MovieListActivity : BaseActivity() , RecyclerItemListener {

    lateinit var binding: ActivityMovieListBinding;

    @Inject
    lateinit var viewModelFactory: ViewModelFactory;


    lateinit var movieViewModel: MovieListViewModel;


    override fun initViewBinding() {
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initToolBar() {
       // setUpIconVisibility(true)
        setTitle("get data activity")
        setSettingsIconVisibility(false)
        setRefreshVisibility(false)
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
                Log.e("done","done");
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
        observe(movieViewModel.getMovieLiveData(),::handleMovieList)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rvMovies.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        movieViewModel.getMovieFromServer();


    }


    private fun bindListData(list: List<Movie>) {
        if (!(list.isNullOrEmpty())) {
            rvMovies.adapter = MovieAdapter(list,this);
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

    override fun onItemSelected(movie: Movie) {

        val intent=Intent(this,MovieDetailsActivity::class.java)
        intent.putExtra(Constants.Movie_ITEM_KEY,movie);
        startActivity(intent);

    }


}