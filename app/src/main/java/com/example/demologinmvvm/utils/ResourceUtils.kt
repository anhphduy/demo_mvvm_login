package com.example.demologinmvvm.utils

import com.example.demologinmvvm.DemoApplication

object ResourceUtils {
    @JvmStatic
    fun getString(resourceId: Int): String {
        return DemoApplication.instance()!!.getString(resourceId)
    }
}