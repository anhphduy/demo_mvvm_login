package com.example.demologinmvvm.data.repository

import com.example.demologinmvvm.R
import com.example.demologinmvvm.common.DataResult
import com.example.demologinmvvm.data.domain.AuthenRepository
import com.example.demologinmvvm.data.local.dao.UserDao
import com.example.demologinmvvm.data.model.User
import com.example.demologinmvvm.utils.LOGIN_USER_KEY
import com.example.demologinmvvm.utils.PreferenceHelper
import com.example.demologinmvvm.utils.ResourceUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenRepositoryImpl @Inject constructor(private val dao: UserDao) : AuthenRepository {

    override suspend fun login(email: String, password: String): DataResult<Boolean?> {
        // In this demo we will get data from local.
        // If get data from servers, implement Retrofit2, inject data source from remote and call api to get data
        return try {
            //Get user from local database
            val user = dao.loadUser(email, password)
            //check if has user, save it to shared preference and return result success
            if (user != null) {
                PreferenceHelper.putObject(LOGIN_USER_KEY, user)
                DataResult.success(data = true)
            } else
            // if not have user, return result fail
                DataResult.success(message = ResourceUtils.getString(R.string.login_error), data = false)
        } catch (e: Exception) {
            // if it has error, return result fail
            DataResult.error(
                data = false,
                message = e.message ?: ResourceUtils.getString(R.string.common_error)
            )
        }
    }

    override suspend fun signUp(
        fullName: String,
        email: String,
        password: String
    ): DataResult<Boolean?> {
        return try {
            //Try to load user to check if it exist
            val userAccount = dao.loadUserByEmail(email)
            if (userAccount != null) {
                // it existed, return result false with message
                DataResult.success(message = ResourceUtils.getString(R.string.account_exist), data = false)
            } else {
                //if not exist, save to local
                val newUser = User(fullName, email, password)
                dao.save(newUser)
                // then put it to shared preference
                PreferenceHelper.putObject(LOGIN_USER_KEY, newUser)
                // return result success
                DataResult.success(data = true)
            }
        } catch (e: Exception) {
            // if it has error, return result fail
            DataResult.error(
                data = false,
                message = e.message ?: ResourceUtils.getString(R.string.common_error)
            )
        }
    }

    override suspend fun getAllUser(): List<User>? = dao.getAllUser()
}