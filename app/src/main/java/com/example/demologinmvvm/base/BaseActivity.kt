package com.example.demologinmvvm.base

import android.os.Bundle
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

    /**
     * Inject ViewModel factory, which will be used to instantiate
     *              new ViewModels
     */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    /**
     * private variable for binding variable
     */
    private lateinit var mViewDataBinding: B

    /**
     * private variable for viewModel variable
     */
    protected lateinit var mViewModel: V

    /**
     * variable for get view data binding
     */
    val binding: B get() = mViewDataBinding

    /**
     * variable to get instance of viewModel
     */
    val viewModel: V get() = mViewModel

    /**
     * function to inject viewModel for each screen
     */
    abstract fun injectViewModel()

    /**
     * function to init binding viewModel
     */
    abstract fun initViewModel(viewModelCallbackManager: V)

    /**
     * function to that will be called after setup everything
     */
    abstract fun onViewReady()

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        // make status bar be transparent
        setTransparentStatusBar()
        injectViewModel()
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutResourceId())
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
        initViewModel(viewModel)
        onViewReady()
    }

    /**
     * Function to show loading
     */
    fun showProgress() {
        LoadingView.show(this)
    }

    /**
     * Function to dismiss loading
     */
    fun dismissProgress() {
        LoadingView.dismiss()
    }

    /**
     * Function to show error dialog
     */
    fun showErrorDialog(msg: String) {
        ErrorDialog(this, title = msg).show()
    }

}