package com.example.demologinmvvm.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.demologinmvvm.utils.USER_TABLE_NAME
import kotlinx.android.parcel.Parcelize

/**
 * Class for User object and create user table for Room database
 */
@Entity(
    tableName = USER_TABLE_NAME,
    indices = [Index(value = ["email"], unique = true)]
)
@Parcelize
data class User(
    val fullName: String = "",
    @PrimaryKey(autoGenerate = false)
    val email: String = "",
    val password: String = ""
) : Parcelable