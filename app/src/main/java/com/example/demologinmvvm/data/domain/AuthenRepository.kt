package com.example.demologinmvvm.data.domain

import com.example.demologinmvvm.common.DataResult
import com.example.demologinmvvm.data.model.User

interface AuthenRepository {
    suspend fun login(email: String, password: String): DataResult<Boolean?>

    suspend fun signUp(fullName: String, email: String, password: String): DataResult<Boolean?>

    suspend fun getAllUser(): List<User>?
}