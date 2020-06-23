package com.mj.movieexample.ui.component.movieList

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mj.movieexample.R
import com.mj.movieexample.core.MyApp
import com.mj.movieexample.data.Result
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.databinding.ActivityMovieListBinding
import com.mj.movieexample.ui.base.BaseActivity
import com.mj.movieexample.ui.base.PaginationScrollListener
import com.mj.movieexample.ui.component.movieList.movieAdapter.MovieAdapter
import com.mj.movieexample.ui.component.movieList.viewModel.MovieListViewModel
import com.mj.movieexample.ui.base.ViewModelFactory
import com.mj.movieexample.ui.base.listeners.RecyclerItemListener
import com.mj.movieexample.ui.component.movieDetails.MovieDetailsActivity
import com.mj.movieexample.util.*
import kotlinx.android.synthetic.main.activity_movie_list.*
import javax.inject.Inject

class MovieListActivity : BaseActivity(), RecyclerItemListener {
    var isLoading: Boolean = false
    private lateinit var movieAdapter: MovieAdapter

    private lateinit var binding: ActivityMovieListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var movieViewModel: MovieListViewModel

    lateinit var linearLayoutManager: LinearLayoutManager

    override fun initViewBinding() {
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initToolBar() {
        setSettingsIconVisibility(false)
        setRefreshVisibility(false)
    }

    override fun injectActivity(baseActivity: BaseActivity) {
        MyApp.getInstance().getMovieComponent().inject(this)
    }

    override fun initializeViewModel() {
        movieViewModel =
            ViewModelProvider(this, viewModelFactory).get(MovieListViewModel::class.java)

    }

    private fun handleMovieList(result: Result<List<Movie>>) {
        isLoading = false
        when (result) {
            is Result.Success -> {
                showLoadingProgress(false)
                bindListData(result.data!!)
                setTitle("" + rvMovies.adapter?.itemCount)
            }
            is Result.NetworkGeneralError -> {
                showLoadingProgress(false)
                Toast.makeText(this, result.msg, Toast.LENGTH_LONG).show()
            }
            is Result.InProgress -> {
                isLoading = true
                showLoadingProgress(true)
            }
            is Result.NetworkNoInternetError -> {
                showLoadingProgress(false)
                Toast.makeText(this, getString(R.string.lbl_no_error_connection), Toast.LENGTH_LONG)
                    .show()

            }
        }

    }

    override fun observeViewModel() {
        observe(movieViewModel.getLiveData(), ::handleMovieList)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRecyclerView()
        setRecyclerViewScrollListener()
       // movieViewModel.getMovieFromServer()

    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        movieAdapter = MovieAdapter(this)
        rvMovies.layoutManager = linearLayoutManager
        rvMovies.adapter = movieAdapter

    }

    private fun setRecyclerViewScrollListener() {
        rvMovies?.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                movieViewModel.getMovieFromServer()
            }
        })


    }


    private fun bindListData(list: List<Movie>) {
        if (!(list.isNullOrEmpty())) {
            movieAdapter.setItems(list)
            movieAdapter.notifyDataSetChanged()
            showDataView(true)
        } else {
            showDataView(false)
        }
    }


    private fun showDataView(isVisible: Boolean) {
        // tvNoData.visibility = if (isVisible) View.GONE else View.VISIBLE
        rvMovies.visibility = if (isVisible) View.VISIBLE else View.GONE
    }


    private fun showLoadingProgress(isVisible: Boolean) {
        progress.visibility = when (isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    override fun onItemSelected(movie: Movie) {

        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra(Constants.Movie_ITEM_KEY, movie)
        startActivity(intent)
    }


}