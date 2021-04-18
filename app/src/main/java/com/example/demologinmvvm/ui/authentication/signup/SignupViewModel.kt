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
    /**
     * fullNameSignup will auto get data from view
     */
    var fullNameSignup: String = ""
        @Bindable get
        set(value) {
            if (field == value)
                return
            field = value
            notifyPropertyChanged(BR.fullNameSignup)
        }

    /**
     * emailSignup will auto get data from view
     */
    var emailSignup: String = ""
        @Bindable get
        set(value) {
            if (field == value)
                return
            field = value
            notifyPropertyChanged(BR.emailSignup)
        }

    /**
     * passwordSignup will auto get data from view
     */
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

    /**
     * set data result for signUp function
     */
    private fun setResultUserSignup(result: DataResult<Boolean?>) {
        _moveCommand.postValue(result)
    }

    /**
     * function handle signUp function with coroutines
     */
    fun signUp() {
        viewModelScope.launch(Dispatchers.IO) {
            // set status for login result is loading
            setResultUserSignup(DataResult.loading())
            try {
                // notify signUp result with result returned from repository
                setResultUserSignup(repository.signUp(fullNameSignup, emailSignup, passwordSignup))
            } catch (exception: Exception) {
                // set status for result is error
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