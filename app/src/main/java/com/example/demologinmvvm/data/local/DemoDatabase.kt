package com.example.demologinmvvm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demologinmvvm.data.local.dao.UserDao
import com.example.demologinmvvm.data.model.User
import com.example.demologinmvvm.utils.DATABASE_NAME

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class DemoDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var instance: DemoDatabase? = null

        fun getInstance(context: Context): DemoDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): DemoDatabase {
            return Room.databaseBuilder(context, DemoDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}