package com.example.demologinmvvm.data.domain

import com.example.demologinmvvm.data.model.User

interface UserRepository {
    fun getCurrentLoginUser(): User?
    fun hadLoginUser(): Boolean
}