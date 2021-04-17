package com.example.demologinmvvm.ui.authentication.signup

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.demologinmvvm.R
import com.example.demologinmvvm.base.BaseViewModel
import com.example.demologinmvvm.common.DataResult
import com.example.demologinmvvm.data.domain.AuthenRepository
import com.example.demologinmvvm.data.model.User
import com.example.demologinmvvm.utils.ResourceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignupViewModel @Inject constructor(private val repository: AuthenRepository) :
    BaseViewModel() {
    var fullNameSignup: String = ""
        @Bindable get
        set(value) {
            if (field == value)
                return
            field = value
            notifyPropertyChanged(BR.fullNameSignup)
        }

    var emailSignup: String = ""
        @Bindable get
        set(value) {
            if (field == value)
                return
            field = value
            notifyPropertyChanged(BR.emailSignup)
        }

    var passwordSignup: String = ""
        @Bindable get
        set(value) {
            if (field == value)
                return
            field = value
            notifyPropertyChanged(BR.passwordSignup)
        }

    private val _moveCommand = MutableLiveData<DataResult<Boolean?>>()
    val moveCommand: LiveData<DataResult<Boolean?>>
        get() = _moveCommand

    private fun setResultUserSignup(result: DataResult<Boolean?>) {
        _moveCommand.postValue(result)
    }

    fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            setResultUserSignup(DataResult.loading())
            try {
                setResultUserSignup(repository.signUp(fullNameSignup, emailSignup, passwordSignup))
            } catch (exception: Exception) {
                setResultUserSignup(
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