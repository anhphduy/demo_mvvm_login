package com.example.demologinmvvm.di.module

import android.app.Application
import com.example.demologinmvvm.DemoApplication
import com.example.demologinmvvm.data.domain.AuthenRepository
import com.example.demologinmvvm.data.domain.UserRepository
import com.example.demologinmvvm.data.local.DemoDatabase
import com.example.demologinmvvm.data.local.dao.UserDao
import com.example.demologinmvvm.data.repository.AuthenRepositoryImpl
import com.example.demologinmvvm.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideApplication(app: DemoApplication): Application = app

    @Provides
    @Singleton
    fun provideRoomDB(app: Application) = DemoDatabase.getInstance(app)

    @Provides
    @Singleton
    fun provideUserDao(db: DemoDatabase) = db.userDao()

    @Provides
    @Singleton
    fun provideAuthenRepository(dao: UserDao): AuthenRepository = AuthenRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = UserRepositoryImpl()

}