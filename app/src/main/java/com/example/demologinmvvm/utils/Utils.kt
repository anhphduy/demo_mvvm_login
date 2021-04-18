package com.example.demologinmvvm.utils

import android.text.TextUtils
import android.util.Patterns

object Utils {
    /**
     * function to validate email with email form
     */
    @JvmStatic
    fun isValidEmail(email: String?): Boolean {
        return email != null && !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(
            email
        ).matches()
    }

    /**
     * function to validate password
     */
    @JvmStatic
    fun isValidPassword(password: String?): Boolean {
        // password need at least 6 characters
        return password != null && password.length >= 6
    }
}