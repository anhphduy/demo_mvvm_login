package com.example.demologinmvvm.data.local.dao

import androidx.room.*
import com.example.demologinmvvm.data.model.User

/**
 * declare ROOM User data access object
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun save(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    suspend fun loadUser(email: String, password: String) : User?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun loadUserByEmail(email: String) : User?

    @Query("SELECT * FROM users")
    suspend fun getAllUser() : List<User>
}