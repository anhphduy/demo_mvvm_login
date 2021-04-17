package com.example.demologinmvvm

import com.example.demologinmvvm.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class DemoApplication : DaggerApplication() {
    init {
        instance = this
    }

    companion object {
        private var instance: DemoApplication? = null

        fun instance() : DemoApplication? {
            return instance
        }
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().application(this).build()

}