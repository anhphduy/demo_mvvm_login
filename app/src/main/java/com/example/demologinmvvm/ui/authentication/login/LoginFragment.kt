package com.example.demologinmvvm.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import com.example.demologinmvvm.ui.home.MainActivity
import com.example.demologinmvvm.R
import com.example.demologinmvvm.base.BaseFragment
import com.example.demologinmvvm.common.DataResult
import com.example.demologinmvvm.databinding.FragmentLoginBinding
import com.example.demologinmvvm.di.injectViewModel
import com.example.demologinmvvm.ui.authentication.signup.SignupFragment
import com.example.demologinmvvm.utils.Utils.isValidEmail
import com.example.demologinmvvm.utils.Utils.isValidPassword
import com.example.demologinmvvm.utils.addFragment
import com.example.demologinmvvm.utils.hideKeyboard

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_login

    override fun onViewReady(bundle: Bundle?) {
        setupTextInputWatcher()
        setupButtonListener()
        observeResultLogin()
    }

    /**
     * function to observe resultLogin and handle it if resultLogin changes status
     */
    private fun observeResultLogin() {
        viewModel.moveCommand.observe(viewLifecycleOwner, Observer {
            val authenActivity = getAuthenActivity() ?: return@Observer
            when (it.status) {
                DataResult.Status.SUCCESS -> {
                    //hide loading indicator + navigate to home screen
                    authenActivity.dismissProgress()
                    if (it.data != null && it.data) {
                        //login success
                        startActivity(Intent(authenActivity, MainActivity::class.java))
                    }
                }

                DataResult.Status.LOADING -> {
                    //show loading indicator
                    authenActivity.showProgress()
                }

                DataResult.Status.ERROR -> {
                    //hide loading indicator + show error
                    authenActivity.dismissProgress()
                    authenActivity.showErrorDialog(it.message ?: getString(R.string.common_error))
                }
            }
        })
    }

    /**
     * function to setup to handle user button click event
     */
    private fun setupButtonListener() {
        val authenActivity = getAuthenActivity()
        binding.btnLogin.setOnClickListener {
            hideKeyboard()
            //need validate email and password before sign in
            if (!isValidEmail(binding.edEmailLogin.text.toString())) {
                setEmailLayoutError(true)
                return@setOnClickListener
            } else {
                setEmailLayoutError(false)
            }

            if (!isValidPassword(binding.edPwLogin.text.toString())) {
                setPasswordLayoutError(true)
                return@setOnClickListener
            } else {
                setPasswordLayoutError(false)
            }
            // if email and password validated, call function to login
            viewModel.login()
        }

        // click button signUp to go to SignUp screen
        binding.btnSignUp.setOnClickListener {
            hideKeyboard()
            authenActivity?.addFragment(SignupFragment(), R.id.authenFragmentContainer)
        }
    }

    /**
     * function setup to listen text input changed event
     */
    private fun setupTextInputWatcher() {
        binding.edEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (text == null)
                    return
                //validate email and show error if it is invalid
                setEmailLayoutError(!isValidEmail(text.toString()))
            }
        })

        binding.edPwLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (text == null)
                    return
                //validate password and show error if it is invalid
                setPasswordLayoutError(!isValidPassword(text.toString()))
            }
        })
    }

    /**
     * function to show/hide error for email
     */
    private fun setEmailLayoutError(hasError: Boolean) {
        binding.apply {
            tILEmailLogin.error = if (hasError) getString(R.string.email_error) else ""
            tILEmailLogin.isErrorEnabled = hasError
        }
    }

    /**
     * function to show/hide error for password
     */
    private fun setPasswordLayoutError(hasError: Boolean) {
        binding.apply {
            tILPasswordLogin.error = if (hasError) getString(R.string.password_error) else ""
            tILPasswordLogin.isErrorEnabled = hasError
        }
    }

    override fun initViewModel(viewModelCallbackManager: LoginViewModel) {
        binding.viewModel = viewModelCallbackManager
    }

}