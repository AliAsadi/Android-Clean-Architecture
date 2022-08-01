package com.aliasadi.clean.domain.util

/**
 * Created by Ali Asadi on 13/05/2020
 */
sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: Throwable) : Result<T>()
}

inline fun <T, R> Result<T>.getResult(success: (Result.Success<T>) -> R, error: (Result.Error<T>) -> R): R =
    if (this is Result.Success) success(this) else error(this as Result.Error)