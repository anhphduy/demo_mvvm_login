package com.example.demologinmvvm.ui.home

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.util.StringUtil
import com.example.demologinmvvm.R
import com.example.demologinmvvm.base.BaseViewModel
import com.example.demologinmvvm.data.domain.UserRepository
import com.example.demologinmvvm.utils.ResourceUtils
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    var userName: String = ""
        @Bindable get
        set(value) {
            if (field == value)
                return
            field = value
            notifyPropertyChanged(BR.userName)
        }

    fun getCurrentUserLogin() {
        val user = userRepository.getCurrentLoginUser()
        userName = user?.fullName ?: ""
    }
}