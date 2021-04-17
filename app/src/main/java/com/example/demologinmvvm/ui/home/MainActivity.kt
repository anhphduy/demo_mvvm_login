package com.example.demologinmvvm.ui.home

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.demologinmvvm.R
import com.example.demologinmvvm.base.BaseActivity
import com.example.demologinmvvm.data.model.User
import com.example.demologinmvvm.databinding.ActivityMainBinding
import com.example.demologinmvvm.databinding.ActivitySplashBinding
import com.example.demologinmvvm.di.injectViewModel
import com.example.demologinmvvm.ui.authentication.AuthenActivity
import com.example.demologinmvvm.ui.splash.SplashViewModel
import com.example.demologinmvvm.utils.LOGIN_USER_KEY
import com.example.demologinmvvm.utils.PreferenceHelper
import com.example.demologinmvvm.utils.setTransparentStatusBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun onViewReady() {
        setupButtonClick()
        viewModel.getCurrentUserLogin()
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_main

    private fun setupButtonClick() {
        btnLogout.setOnClickListener {
            val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        dialog.dismiss()
                        val intent = Intent(this, AuthenActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        PreferenceHelper.clear()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                        dialog.dismiss()
                    }
                }
            }

            val builder = AlertDialog.Builder(this)
            builder.setMessage(getString(R.string.log_out_message)).setPositiveButton(getString(
                R.string.yes
            ), dialogClickListener)
                .setNegativeButton(getString(R.string.no), dialogClickListener).show()
        }
    }

    override fun initViewModel(viewModelCallbackManager: MainViewModel) {
        binding.viewModel = viewModelCallbackManager
    }
}