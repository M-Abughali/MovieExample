package com.mj.movieexample.ui.component.movieList.movieAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mj.movieexample.R
import com.mj.movieexample.databinding.RowItem2Binding
import com.mj.movieexample.databinding.RowItemBinding
import com.mj.movieexample.data.model.Movie
import com.mj.movieexample.ui.base.listeners.RecyclerItemListener

class MovieAdapter(val list: List<Movie>,val recyclerItemListener: RecyclerItemListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private final val VIEW_TYPE1 = 1;
    private final val VIEW_TYPE2 = 2;



    override fun getItemViewType(position: Int): Int {
        if (list.get(position).title.startsWith("A"))
            return VIEW_TYPE1
        else
            return VIEW_TYPE2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE1) {
            val rowItemBinding = DataBindingUtil.inflate<RowItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.row_item,
                parent,
                false
            )
            return viewHolder(rowItemBinding,recyclerItemListener);
        } else {
            val rowItemBinding2 = DataBindingUtil.inflate<RowItem2Binding>(
                LayoutInflater.from(parent.context),
                R.layout.row_item2,
                parent,
                false
            )
            return viewHolder2(rowItemBinding2,recyclerItemListener);
        }

    }

    override fun getItemCount(): Int {
        return list.size;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == VIEW_TYPE1) {
            holder as viewHolder
            holder.bind(list.get(position))

        } else if (holder.itemViewType == VIEW_TYPE2) {
            holder as viewHolder2
            holder.bind(list.get(position))

        }


    }
}