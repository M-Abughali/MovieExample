package com.mj.movieexample.ui.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mj.movieexample.R

abstract class BaseFragment : Fragment() {

    protected var baseViewModel: BaseViewModel? = null

    abstract val layoutId: Int

    protected abstract fun initializeDagger()

    protected abstract fun initializePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeDagger()
        initializePresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    fun setTitle(title: String) {
        val actionBar = (activity as BaseActivity).supportActionBar
        if (actionBar != null) {
            val titleTextView = activity?.findViewById<TextView>(R.id.txtToolbarTitle)
            if (title.isNotEmpty()) {
                titleTextView?.text = title
            }
        }
    }
}
