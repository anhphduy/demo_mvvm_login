package com.example.demologinmvvm.di.module

import com.example.demologinmvvm.ui.home.MainActivity
import com.example.demologinmvvm.ui.authentication.AuthenActivity
import com.example.demologinmvvm.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module declare Activities that is AndroidInjector
 */
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributesSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesAuthenActivity(): AuthenActivity

}