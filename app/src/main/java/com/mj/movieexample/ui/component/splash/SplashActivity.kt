package com.mj.movieexample.ui.component.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mj.movieexample.R
import com.mj.movieexample.databinding.ActivitySplashBinding
import com.mj.movieexample.ui.base.BaseActivity
import com.mj.movieexample.ui.component.movieList.MovieListActivity
import com.mj.movieexample.util.Constants

class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding;
    override fun injectActivity(baseActivity: BaseActivity) {

    }

    override fun initializeViewModel() {}

    override fun observeViewModel() {}

    override fun initViewBinding() {
        binding=ActivitySplashBinding.inflate(layoutInflater);
        setContentView(binding.root)
    }

    override fun initToolBar() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMainScreen()
    }


    private fun navigateToMainScreen() {
        Handler().postDelayed({
            val nextScreenIntent = Intent(this, MovieListActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }, Constants.SPLASH_DELAY.toLong())
    }
}