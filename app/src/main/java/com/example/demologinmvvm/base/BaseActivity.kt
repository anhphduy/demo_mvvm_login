package com.example.demologinmvvm.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demologinmvvm.R
import com.example.demologinmvvm.custom.error.ErrorDialog
import com.example.demologinmvvm.custom.loading.LoadingView
import com.example.demologinmvvm.utils.setTransparentStatusBar
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding, V : ViewModel> : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mViewDataBinding: B
    protected lateinit var mViewModel: V

    val binding: B get() = mViewDataBinding
    val viewModel: V get() = mViewModel

    abstract fun injectViewModel()
    abstract fun initViewModel(viewModelCallbackManager: V)
    abstract fun onViewReady()

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setTransparentStatusBar()
        injectViewModel()
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutResourceId())
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
        initViewModel(viewModel)
        onViewReady()
    }

    fun showProgress() {
        LoadingView.show(this)
    }

    fun dismissProgress() {
        LoadingView.dismiss()
    }

    fun showErrorDialog(msg: String) {
        ErrorDialog(this, title = msg).show()
    }

}