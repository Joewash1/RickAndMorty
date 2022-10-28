package com.example.rickandmorty.model

import retrofit2.Response

data class SimpleResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: java.lang.Exception?
){
    companion object{
        fun <T> success(data: Response<T>): SimpleResponse<T>{
            return SimpleResponse(
                status = Status.Success,
                data = data,
                exception = null
            )
        }

        fun <T> failure(exception: java.lang.Exception): SimpleResponse<T> {
            return SimpleResponse(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }
    }

    sealed class Status{
        object Success : Status()
        object Failure : Status()
    }

    val failed: Boolean
        get() = this.status == Status.Failure

    val isSuccessful: Boolean
    get() = !failed && this.data?.isSuccessful == true

    val body: T
        get() = this.data!!.body()!!
}
