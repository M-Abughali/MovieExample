package com.mj.movieexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mj.movieexample.core.MyApp
import com.mj.movieexample.R
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

            when (it) {
                is Result.Success -> {
                    getData.setText("Success = " + it.data?.size)
                    Log.e("123", "")
                }
                is Result.Fail -> {
                    getData.setText("Fail")

                    Log.e("444", "")
                }
                Result.InProgrss -> {
                    getData.setText("InProgrss")

                    Log.e("123", "")
                }
            };

        })

        getData.setOnClickListener {
            CoroutineScope(IO).launch {
                movieViewModel.getMovieFromServer();
            }

        }

    }
}