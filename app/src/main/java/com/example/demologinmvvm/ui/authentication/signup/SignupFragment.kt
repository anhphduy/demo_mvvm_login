package com.example.demologinmvvm.ui.authentication.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.demologinmvvm.R
import com.example.demologinmvvm.base.BaseFragment
import com.example.demologinmvvm.common.DataResult
import com.example.demologinmvvm.databinding.FragmentSignupBinding
import com.example.demologinmvvm.di.injectViewModel
import com.example.demologinmvvm.utils.Utils

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

    private fun observeResultSignup() {
        viewModel.moveCommand.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it.status) {
                DataResult.Status.SUCCESS -> {
                    if (it.data != null && it.data) {
                        Toast.makeText(requireContext(), "dang ky thanh cong", Toast.LENGTH_SHORT)
                            .show()
                    } else {
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
        binding.btnCreate.setOnClickListener {
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
            viewModel.signUp()
        }
    }

    private fun setupTextInputWatcher() {
        binding.edFullNameSignup.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setFullNameLayoutError(text.isNullOrEmpty())
            }
        })

        binding.edEmailSignup.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setEmailLayoutError(!Utils.isValidEmail(text.toString()))
            }
        })

        binding.edPwSignup.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setPasswordLayoutError(!Utils.isValidPassword(text.toString()))
            }
        })
    }

    private fun setEmailLayoutError(hasError: Boolean) {
        binding.apply {
            tILEmailSignup.error = if (hasError) getString(R.string.email_error) else ""
            tILEmailSignup.isErrorEnabled = hasError
        }
    }

    private fun setPasswordLayoutError(hasError: Boolean) {
        binding.apply {
            tILPasswordSignup.error = if (hasError) getString(R.string.password_error) else ""
            tILPasswordSignup.isErrorEnabled = hasError
        }
    }

    private fun setFullNameLayoutError(hasError: Boolean) {
        binding.apply {
            tILFullNameSignup.error = if (hasError) getString(R.string.full_name_error) else ""
            tILFullNameSignup.isErrorEnabled = hasError
        }
    }
}