package com.example.demologinmvvm.ui.authentication.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.demologinmvvm.R
import com.example.demologinmvvm.base.BaseFragment
import com.example.demologinmvvm.common.DataResult
import com.example.demologinmvvm.databinding.FragmentLoginBinding
import com.example.demologinmvvm.di.injectViewModel
import com.example.demologinmvvm.ui.authentication.AuthenActivity
import com.example.demologinmvvm.ui.authentication.signup.SignupFragment
import com.example.demologinmvvm.utils.Utils.isValidEmail
import com.example.demologinmvvm.utils.Utils.isValidPassword
import com.example.demologinmvvm.utils.addFragment

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

    private fun observeResultLogin() {
        viewModel.moveCommand.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                DataResult.Status.SUCCESS -> {
                    if (it.data != null && it.data) {
                        //login success
                        Toast.makeText(requireContext(), "dang nhap thanh cong", Toast.LENGTH_SHORT).show()
                    } else {
                        // login failed
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    //hide loading indicator + handle data
                }

                DataResult.Status.LOADING -> {
                    //show loading indicator
                    Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                }

                DataResult.Status.ERROR -> {
                    //hide loading indicator + show error
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun setupButtonListener() {
        binding.btnLogin.setOnClickListener {
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
            viewModel.login()
        }

        binding.btnSignUp.setOnClickListener {
            if (activity != null && activity is AuthenActivity)
                (activity as AuthenActivity).addFragment(
                    SignupFragment(),
                    R.id.authenFragmentContainer
                )
        }
    }

    private fun setupTextInputWatcher() {
        binding.edEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (text == null)
                    return
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
                setPasswordLayoutError(!isValidPassword(text.toString()))
            }
        })
    }

    private fun setEmailLayoutError(hasError: Boolean) {
        binding.apply {
            tILEmailLogin.error = if (hasError) getString(R.string.email_error) else ""
            tILEmailLogin.isErrorEnabled = hasError
        }
    }

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