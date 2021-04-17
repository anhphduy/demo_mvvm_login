package com.example.demologinmvvm.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demologinmvvm.base.BaseViewModel
import com.example.demologinmvvm.data.domain.UserRepository
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    private val _moveCommand = MutableLiveData<Boolean>()
    val moveCommand: LiveData<Boolean>
        get() = _moveCommand

    fun navigate() {
        _moveCommand.value = userRepository.hadLoginUser()
    }
}