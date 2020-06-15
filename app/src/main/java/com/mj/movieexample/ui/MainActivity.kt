package com.mj.movieexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mj.movieexample.core.MyApp
import com.mj.movieexample.R
import com.mj.movieexample.ui.movieAdapter.MovieAdapter
import com.mj.movieexample.ui.viewModel.DisplayMovieViewModel
import com.mj.movieexample.ui.viewModel.ViewModelFactory
import com.mj.movieexample.util.*;
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyApp.getInstance().getMovieComponent().Inject(this);


        val movieViewModel =
            ViewModelProvider(this, viewModelFactory).get(DisplayMovieViewModel::class.java);
        movieViewModel.getMovieLiveData().observe(this, Observer {

            rvMovies.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);

            when (it) {
                is Result.Success -> {
                    showProgrss(false)
                    rvMovies.adapter = MovieAdapter(it.data!!);
                }
                is Result.Fail -> {
                    showProgrss(false)
                    Toast.makeText(this, "faild", Toast.LENGTH_LONG).show();
                }
                Result.InProgrss -> {
                    showProgrss(true)
                    Toast.makeText(this, "InProgrss", Toast.LENGTH_LONG).show();
                }
            };


        })
        CoroutineScope(IO).launch {
            movieViewModel.getMovieFromServer();
        }


    }


    private fun showProgrss(isVisible: Boolean) {
        progress.visibility = when (isVisible) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

}