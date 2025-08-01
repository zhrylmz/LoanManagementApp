package com.loanmanagementapp.network

import com.loanmanagementapp.contract.model.AppResult
import retrofit2.Response

inline val <T> Response<T>.asAppResult: AppResult<T>
    get() {
        return if (isSuccessful) {
            body()?.let { data ->
                AppResult.Success(data)
            } ?: run {
                createErrorResult(this)
            }
        } else {
            createErrorResult(this)
        }
    }

fun <T> createErrorResult(response: Response<T>): AppResult.Error {
    return AppResult.Error(
        Exception(response.message())
    )
}
