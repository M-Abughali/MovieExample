package com.mj.movieexample.ui.component.movieDetails

import android.os.Bundle
import com.mj.movieexample.R
import com.mj.movieexample.core.MyApp
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.databinding.ActivityMovieDetailsBinding
import com.mj.movieexample.ui.base.BaseActivity
import com.mj.movieexample.util.Constants
import com.squareup.picasso.Picasso

class MovieDetailsActivity : BaseActivity() {
    lateinit var binding: ActivityMovieDetailsBinding;

    override fun injectActivity(baseActivity: BaseActivity) {
        MyApp.getInstance().getMovieComponent().inject(this)
    }

    override fun initializeViewModel() {}

    override fun observeViewModel() {}

    override fun initViewBinding() {
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initToolBar() {
        setTitle("Display activity")
        setSettingsIconVisibility(false)
        setRefreshVisibility(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val movie = intent.extras?.getSerializable(Constants.Movie_ITEM_KEY) as Movie
        binding.movieItem = movie;
        Picasso.get().load(Constants.MAIN_IMAGE_URL + movie.poster_path)
            .error(R.drawable.error_image_place_holder)
            .placeholder(R.drawable.image_place_holder).into(binding.movieImage);


    }
}