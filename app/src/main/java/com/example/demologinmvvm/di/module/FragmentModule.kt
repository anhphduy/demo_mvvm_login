package com.example.demologinmvvm.di.module

import com.example.demologinmvvm.ui.authentication.login.LoginFragment
import com.example.demologinmvvm.ui.authentication.signup.SignupFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributesSignupFragment(): SignupFragment


}