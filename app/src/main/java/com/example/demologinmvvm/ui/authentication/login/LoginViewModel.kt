package com.example.demologinmvvm.ui.authentication.login

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.demologinmvvm.R
import com.example.demologinmvvm.base.BaseViewModel
import com.example.demologinmvvm.common.DataResult
import com.example.demologinmvvm.data.domain.AuthenRepository
import com.example.demologinmvvm.utils.ResourceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: AuthenRepository) :
    BaseViewModel() {
    var email: String = ""
        @Bindable get
        set(value) {
            if (field == value)
                return
            field = value
            notifyPropertyChanged(BR.email)
        }

    var password: String = ""
        @Bindable get
        set(value) {
            if (field == value)
                return
            field = value
            notifyPropertyChanged(BR.password)
        }

    private val _moveCommand = MutableLiveData<DataResult<Boolean?>>()
    val moveCommand: LiveData<DataResult<Boolean?>>
        get() = _moveCommand

    private fun setResultLogin(result: DataResult<Boolean?>) {
        _moveCommand.postValue(result)
    }

    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            setResultLogin(DataResult.loading())
            try {
                setResultLogin(repository.login(email, password))
            } catch (exception: Exception) {
                setResultLogin(
                    DataResult.error(
                        data = null,
                        message = exception.message
                            ?: ResourceUtils.getString(R.string.common_error)
                    )
                )
            }
        }
    }
}