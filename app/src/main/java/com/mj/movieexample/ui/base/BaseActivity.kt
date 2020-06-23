package com.mj.movieexample.ui.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.mj.movieexample.ui.base.listeners.ActionBarView
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity : AppCompatActivity(), ActionBarView {


    protected abstract fun injectActivity(baseActivity: BaseActivity)
    protected abstract fun initializeViewModel()
    protected abstract fun observeViewModel()
    protected abstract fun initViewBinding()
    protected abstract fun initToolBar()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        initViewBinding()
        initToolBar()
        injectActivity(this)
        initializeViewModel()
        observeViewModel()
    }


    override fun setUpIconVisibility(visible: Boolean) {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    override fun setTitle(titleKey: String) {
        txtToolbarTitle.text = titleKey
    }

    override fun setSettingsIconVisibility(visibility: Boolean) {
        icToolbarSetting.visibility = if (visibility) VISIBLE else GONE
    }

    override fun setRefreshVisibility(visibility: Boolean) {
        icToolbarRefresh.visibility = if (visibility) VISIBLE else GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
