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
    /**
     * email will auto get data from view
     */
    var email: String = ""
        @Bindable get
        set(value) {
            if (field == value)
                return
            field = value
            notifyPropertyChanged(BR.email)
        }

    /**
     * password will get data from view
     */
    var password: String = ""
        @Bindable get
        set(value) {
            if (field == value)
                return
            field = value
            notifyPropertyChanged(BR.password)
        }

    /**
     * data result for login function
     */
    private val _moveCommand = MutableLiveData<DataResult<Boolean?>>()
    val moveCommand: LiveData<DataResult<Boolean?>>
        get() = _moveCommand

    /**
     * set data result for login function
     */
    private fun setResultLogin(result: DataResult<Boolean?>) {
        //notify login result changed
        _moveCommand.postValue(result)
    }

    /**
     * function handle login function with coroutines
     */
    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            // set status for login result is loading
            setResultLogin(DataResult.loading())
            try {
                // notify login result with result returned from repository
                setResultLogin(repository.login(email, password))
            } catch (exception: Exception) {
                // set status for result is error
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