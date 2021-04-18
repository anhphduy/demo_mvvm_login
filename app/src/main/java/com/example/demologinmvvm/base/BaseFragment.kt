package com.example.demologinmvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demologinmvvm.R
import com.example.demologinmvvm.ui.authentication.AuthenActivity
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, V : ViewModel> : DaggerDialogFragment() {

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

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.AppTheme_Fragment)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        dialog?.window?.attributes?.windowAnimations = R.style.AppTheme_Fragment
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectViewModel()
        mViewDataBinding =
            DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setup binding viewmodel
        initViewModel(viewModel)
        onViewReady(savedInstanceState)
    }

    /**
     * function to that will be called after setup everything
     */
    abstract fun onViewReady(bundle: Bundle?)

    /**
     * function to get AuthenActivity from fragment that show in AuthenActivity
     */
    fun getAuthenActivity(): AuthenActivity? {
        return if (activity is AuthenActivity) activity as AuthenActivity else null
    }
}