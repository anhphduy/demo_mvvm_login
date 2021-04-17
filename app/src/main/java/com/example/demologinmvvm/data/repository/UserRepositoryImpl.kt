package com.example.demologinmvvm.data.repository

import com.example.demologinmvvm.data.domain.UserRepository
import com.example.demologinmvvm.data.model.User
import com.example.demologinmvvm.utils.LOGIN_USER_KEY
import com.example.demologinmvvm.utils.PreferenceHelper

class UserRepositoryImpl : UserRepository{

    override fun getCurrentLoginUser(): User? = PreferenceHelper.getObject(LOGIN_USER_KEY, User::class.java)

    override fun hadLoginUser(): Boolean = getCurrentLoginUser() != null
}