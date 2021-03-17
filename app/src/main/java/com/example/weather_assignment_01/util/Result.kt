package com.example.weather_assignment_01.util

import java.lang.Exception

sealed class Result<out R> {
    data class Success<out T>(val data:T): Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

fun <T>Result<T>.getValue(): T{
    return if(this is Result.Success){
        this.data
    }else{
        throw(this as Result.Error).exception
    }
}