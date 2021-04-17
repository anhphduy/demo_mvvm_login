package com.example.demologinmvvm.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import com.example.demologinmvvm.R
import com.example.demologinmvvm.base.BaseActivity
import com.example.demologinmvvm.databinding.ActivitySplashBinding
import com.example.demologinmvvm.di.injectViewModel
import com.example.demologinmvvm.ui.authentication.AuthenActivity
import com.example.demologinmvvm.ui.home.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun onViewReady() {
        observeHadLogin()
        setupSplashTime()
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_splash

    private fun observeHadLogin() {
        // if had login, go to main screen, if not, go to login screen
        viewModel.moveCommand.observe(this, Observer { hadLogin ->
            startActivity(
                Intent(
                    this,
                    if (hadLogin) MainActivity::class.java else AuthenActivity::class.java
                )
            )
        })
    }

    private fun setupSplashTime() {
        // show splash screen for 2 seconds
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            // after that navigate to next screen
            viewModel.navigate()
            finish()
        }, 2000)
    }

    override fun initViewModel(viewModelCallbackManager: SplashViewModel) {}
}