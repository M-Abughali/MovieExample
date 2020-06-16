package com.mj.movieexample.ui.base.listeners

import com.mj.movieexample.data.model.Movie


interface RecyclerItemListener {
    fun onItemSelected(movie: Movie)
}
