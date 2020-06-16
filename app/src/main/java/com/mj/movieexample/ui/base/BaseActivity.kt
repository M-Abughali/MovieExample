package com.mj.movieexample.ui.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.mj.movieexample.core.MyApp
import com.mj.movieexample.databinding.ToolbarBinding
import com.mj.movieexample.ui.base.listeners.ActionBarView
import com.task.ui.base.BaseViewModel

abstract class BaseActivity : AppCompatActivity(), ActionBarView {


    protected lateinit var toolbarBinding: ToolbarBinding

    protected abstract fun injectActivity(baseActivity: BaseActivity)
    protected abstract fun initializeViewModel()
    protected abstract fun observeViewModel()
    protected abstract fun initViewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        initViewBinding()
        injectActivity(this)
        initializeToolbar()
        initializeViewModel()
        observeViewModel()
    }

    private fun initializeToolbar() {
        toolbarBinding = ToolbarBinding.inflate(layoutInflater)
        toolbarBinding.txtToolbarTitle.text = ""
    }

    override fun setUpIconVisibility(visible: Boolean) {
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    override fun setTitle(titleKey: String) {
        toolbarBinding.txtToolbarTitle.text = titleKey
    }

    override fun setSettingsIconVisibility(visibility: Boolean) {
        toolbarBinding.icToolbarSetting.visibility = if (visibility) VISIBLE else GONE
    }

    override fun setRefreshVisibility(visibility: Boolean) {
        toolbarBinding.icToolbarRefresh.visibility = if (visibility) VISIBLE else GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
