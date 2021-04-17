package com.example.demologinmvvm.common

data class DataResult<out T>(val status: Status, val data : T?, val message : String?) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun<T> success(message: String = "", data: T): DataResult<T> =
            DataResult(status = Status.SUCCESS, data = data, message = message)

        fun<T> error(message: String?, data : T? = null) : DataResult<T> =
            DataResult(status = Status.ERROR, data = data, message = message)

        fun<T> loading(data: T? = null) : DataResult<T> =
            DataResult(status = Status.LOADING, data = data, message = null)
    }
}