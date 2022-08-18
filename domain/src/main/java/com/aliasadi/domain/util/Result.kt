package com.aliasadi.domain.util

/**
 * Created by Ali Asadi on 13/05/2020
 */
sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: Throwable) : Result<T>()
}

inline fun <T, R> Result<T>.getResult(
    success: (Result.Success<T>) -> R,
    error: (Result.Error<T>) -> R
): R = if (this is Result.Success) success(this) else error(this as Result.Error)

inline fun <T> Result<T>.onSuccess(
    block: (T) -> Unit
): Result<T> = if (this is Result.Success) also { block(data) } else this

inline fun <T> Result<T>.onError(
    block: (Throwable) -> Unit
): Result<T> = if (this is Result.Error) also { block(error) } else this
