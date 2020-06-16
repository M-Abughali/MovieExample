package com.mj.movieexample.ui.movieAdapter

import androidx.recyclerview.widget.RecyclerView
import com.mj.movieexample.R
import com.mj.movieexample.databinding.RowItemBinding
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.util.Constants
import com.squareup.picasso.Picasso

class viewHolder(val rowItemBinding: RowItemBinding) :
    RecyclerView.ViewHolder(rowItemBinding.root) {
    fun bind(movie: Movie) {
        Picasso.get().load(Constants.MAIN_IMAGE_URL + movie.poster_path)
            .error(R.drawable.error_image_place_holder)
            .placeholder(R.drawable.image_place_holder).into(rowItemBinding.movieImage);
        rowItemBinding.movieItem = movie
    }

}