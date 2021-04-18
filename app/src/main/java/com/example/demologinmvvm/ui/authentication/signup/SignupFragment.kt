package com.example.demologinmvvm.ui.authentication.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.demologinmvvm.ui.home.MainActivity
import com.example.demologinmvvm.R
import com.example.demologinmvvm.base.BaseFragment
import com.example.demologinmvvm.common.DataResult
import com.example.demologinmvvm.databinding.FragmentSignupBinding
import com.example.demologinmvvm.di.injectViewModel
import com.example.demologinmvvm.utils.Utils
import com.example.demologinmvvm.utils.hideKeyboard

class SignupFragment : BaseFragment<FragmentSignupBinding, SignupViewModel>() {
    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun initViewModel(viewModelCallbackManager: SignupViewModel) {
        binding.viewModel = viewModelCallbackManager
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_signup

    override fun onViewReady(bundle: Bundle?) {
        setupTextInputWatcher()
        setupButtonListener()
        observeResultSignup()
    }

    /**
     * function to observe resultSignUp and handle it if resultSignUp changes status
     */
    private fun observeResultSignup() {
        viewModel.moveCommand.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            val authenActivity = getAuthenActivity() ?: return@Observer
            when (it.status) {
                DataResult.Status.SUCCESS -> {
                    authenActivity.dismissProgress()
                    if (it.data != null && it.data) {
                        //sign up success
                        startActivity( Intent(authenActivity, MainActivity::class.java))
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
        binding.btnCreate.setOnClickListener {
            hideKeyboard()
            //need validate user name, email and password before create new account
            if (binding.edFullNameSignup.text.toString().isEmpty()) {
                setFullNameLayoutError(true)
                return@setOnClickListener
            } else {
                setFullNameLayoutError(false)
            }

            if (!Utils.isValidEmail(binding.edEmailSignup.text.toString())) {
                setEmailLayoutError(true)
                return@setOnClickListener
            } else {
                setEmailLayoutError(false)
            }

            if (!Utils.isValidPassword(binding.edPwSignup.text.toString())) {
                setPasswordLayoutError(true)
                return@setOnClickListener
            } else {
                setPasswordLayoutError(false)
            }
            // if user's information is validated, call function to signUp
            viewModel.signUp()
        }
    }

    /**
     * function setup to listen text input changed event
     */
    private fun setupTextInputWatcher() {
        binding.edFullNameSignup.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //validate user's fullName and show error if it is invalid
                setFullNameLayoutError(text.isNullOrEmpty())
            }
        })

        binding.edEmailSignup.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //validate email and show error if it is invalid
                setEmailLayoutError(!Utils.isValidEmail(text.toString()))
            }
        })

        binding.edPwSignup.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //validate password and show error if it is invalid
                setPasswordLayoutError(!Utils.isValidPassword(text.toString()))
            }
        })
    }

    /**
     * function to show/hide error for email
     */
    private fun setEmailLayoutError(hasError: Boolean) {
        binding.apply {
            tILEmailSignup.error = if (hasError) getString(R.string.email_error) else ""
            tILEmailSignup.isErrorEnabled = hasError
        }
    }

    /**
     * function to show/hide error for password
     */
    private fun setPasswordLayoutError(hasError: Boolean) {
        binding.apply {
            tILPasswordSignup.error = if (hasError) getString(R.string.password_error) else ""
            tILPasswordSignup.isErrorEnabled = hasError
        }
    }

    /**
     * function to show/hide error for fullName
     */
    private fun setFullNameLayoutError(hasError: Boolean) {
        binding.apply {
            tILFullNameSignup.error = if (hasError) getString(R.string.full_name_error) else ""
            tILFullNameSignup.isErrorEnabled = hasError
        }
    }
}