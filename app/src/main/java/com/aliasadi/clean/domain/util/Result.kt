package com.aliasadi.clean.domain.util

/**
 * Created by Ali Asadi on 13/05/2020
 */
sealed class Result<T>(data: T?) {
    data class Success<T>(val data: T) : Result<T>(data)
    data class Error<T>(val error: Throwable) : Result<T>(null)
}