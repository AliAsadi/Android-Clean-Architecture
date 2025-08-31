package com.aliasadi.data.util


suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> = try {
    Result.success(apiCall.invoke())
} catch (e: Exception) {
    Result.failure(e)
}
