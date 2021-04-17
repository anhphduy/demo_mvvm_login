package com.example.demologinmvvm.ui.authentication

import com.example.demologinmvvm.R
import com.example.demologinmvvm.base.BaseActivity
import com.example.demologinmvvm.databinding.ActivityAuthenBinding
import com.example.demologinmvvm.di.injectViewModel
import com.example.demologinmvvm.ui.authentication.login.LoginFragment
import com.example.demologinmvvm.utils.addFragment
import com.example.demologinmvvm.utils.replaceFragment

class AuthenActivity : BaseActivity<ActivityAuthenBinding, AuthenViewModel>() {
    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun onViewReady() {
        //show login fragment as first screen
        openLoginScreen()
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_authen

    private fun openLoginScreen() {
        //add login fragment to the frame
        replaceFragment(LoginFragment(), R.id.authenFragmentContainer, addToBackStack = false)
        binding.authenFragmentContainer
    }

    override fun initViewModel(viewModelCallbackManager: AuthenViewModel) {
    }
}