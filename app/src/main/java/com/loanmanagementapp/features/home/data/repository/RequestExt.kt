package com.loanmanagementapp.features.home.data.repository

import com.loanmanagementapp.contract.model.AppResult
import com.loanmanagementapp.network.asAppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend inline fun <reified T : Any> request(
    crossinline call: suspend () -> Response<T>
): AppResult<T> = withContext(Dispatchers.IO) {
    try {
        call.invoke().asAppResult
    } catch (exception: Exception) {
        AppResult.Error(error = exception)
    }
}