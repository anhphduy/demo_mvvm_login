package com.example.demologinmvvm.ui.splash

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import com.example.demologinmvvm.DemoApplication
import com.example.demologinmvvm.MainActivity
import com.example.demologinmvvm.R
import com.example.demologinmvvm.data.model.User
import com.example.demologinmvvm.ui.authentication.AuthenActivity
import com.example.demologinmvvm.utils.LOGIN_USER_KEY
import com.example.demologinmvvm.utils.PreferenceHelper

class SplashActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupSplashTime()
    }

    private fun navigate(){
        startActivity(Intent(this, AuthenActivity::class.java))

        val user = PreferenceHelper.getObject(LOGIN_USER_KEY, User::class.java)

        if (user == null) {
            startActivity(Intent(this, AuthenActivity::class.java))
        } else{
            startActivity( Intent(this, MainActivity::class.java))
        }
    }

    private fun setupSplashTime() {
        // show splash screen for 2 seconds
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            navigate()
            finish()
        }, 2000)
    }

}