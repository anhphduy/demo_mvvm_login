package com.example.demologinmvvm.di.component

import com.example.demologinmvvm.DemoApplication
import com.example.demologinmvvm.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Class to declare app component for Dagger2
 */
@Singleton
@Component(
    modules = [
        ActivityModule::class,
        AppModule::class,
        NetworkModule::class,
        ViewModelModule::class,
        AndroidSupportInjectionModule::class,
        FragmentModule::class
    ]
)

interface AppComponent : AndroidInjector<DemoApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: DemoApplication): Builder

        fun build(): AppComponent
    }

}