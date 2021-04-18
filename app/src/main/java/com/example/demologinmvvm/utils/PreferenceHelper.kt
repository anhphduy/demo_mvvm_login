package com.example.demologinmvvm.utils

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.demologinmvvm.DemoApplication
import com.google.gson.Gson

/**
 * Class to provide functions to use SharedPreferences
 */
object PreferenceHelper {

    private fun defaultPrefs(): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(DemoApplication.instance())

    fun putObject(key: String, value : Any){
        val gson = Gson()
        val json = gson.toJson(value)
        putString(key, json)
    }

    fun < T : Any>getObject(key: String, objectClass: Class<T> ): T? {
        val json = getString(key, "")
        try {
            val gson = Gson()

            return (gson.fromJson(json, objectClass) as T)
        } catch (e: Exception){
            return null
        }
    }

    fun putString(key: String, value: String) {
        defaultPrefs()[key] = value
    }

    fun getString(key: String, defaultValue: String): String {
        return defaultPrefs()[key, defaultValue] as String
    }

    fun clear(){
        defaultPrefs().clearData()
    }

    private fun SharedPreferences.clearData() {
        this.edit().clear().commit()
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    operator fun SharedPreferences.set(key: String, value: Any?) {

        when (value) {
            is String? -> edit({ it.putString(key, value) })
            is Int -> edit({ it.putInt(key, value) })
            is Boolean -> edit({ it.putBoolean(key, value) })
            is Float -> edit({ it.putFloat(key, value) })
            is Long -> edit({ it.putLong(key, value) })
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    operator inline fun <reified T : Any> SharedPreferences.get(
        key: String,
        defaultValue: T? = null
    ): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }
}