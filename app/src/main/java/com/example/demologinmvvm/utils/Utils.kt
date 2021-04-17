package com.example.demologinmvvm.utils

import android.text.TextUtils
import android.util.Patterns

object Utils {
    @JvmStatic
    fun isValidEmail(email: String?): Boolean {
        return email != null && !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(
            email
        ).matches()
    }

    @JvmStatic
    fun isValidPassword(password: String?): Boolean {
        return password != null && password.length >= 6
    }
}