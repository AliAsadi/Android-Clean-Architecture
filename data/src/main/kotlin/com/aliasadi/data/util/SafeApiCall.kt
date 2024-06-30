package com.aliasadi.data.util

import com.aliasadi.domain.util.Result

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> = try {
    Result.Success(apiCall.invoke())
} catch (e: Exception) {
    Result.Error(e)
}
