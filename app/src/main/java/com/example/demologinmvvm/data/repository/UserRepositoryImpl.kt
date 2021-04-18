package com.example.demologinmvvm.data.repository

import com.example.demologinmvvm.data.domain.UserRepository
import com.example.demologinmvvm.data.model.User
import com.example.demologinmvvm.utils.LOGIN_USER_KEY
import com.example.demologinmvvm.utils.PreferenceHelper

/**
 * Implement functions of UserRepository here and handle logic for user login
 */
class UserRepositoryImpl : UserRepository{
    /**
     * Function to get current login user from SharedPreferences
     */
    override fun getCurrentLoginUser(): User? = PreferenceHelper.getObject(LOGIN_USER_KEY, User::class.java)

    /**
     * Function to check if had an user logged in
     */
    override fun hadLoginUser(): Boolean = getCurrentLoginUser() != null
}