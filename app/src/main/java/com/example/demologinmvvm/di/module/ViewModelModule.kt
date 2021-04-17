package com.example.demologinmvvm.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demologinmvvm.di.ViewModelKey
import com.example.demologinmvvm.di.factory.ViewModelFactory
import com.example.demologinmvvm.ui.authentication.AuthenViewModel
import com.example.demologinmvvm.ui.authentication.login.LoginViewModel
import com.example.demologinmvvm.ui.authentication.signup.SignupViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AuthenViewModel::class)
    internal abstract fun providesAuthenViewModel(viewModel: AuthenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun providesLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignupViewModel::class)
    internal abstract fun providesSignupViewModel(viewModel: SignupViewModel): ViewModel
}