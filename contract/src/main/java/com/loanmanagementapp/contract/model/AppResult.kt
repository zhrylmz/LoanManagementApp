package com.loanmanagementapp.contract.model

sealed class AppResult<out T> {

    class Success<out T>(
        val result: T
    ) : AppResult<T>()

    class Error(
        val error: Exception
    ) : AppResult<Nothing>()

}

inline fun <T> AppResult<T>.onSuccess(action: (T) -> Unit): AppResult<T> {
    if (this is AppResult.Success) action(result)
    return this
}

inline fun <T> AppResult<T>.onError(action: (Exception) -> Unit): AppResult<T> {
    if (this is AppResult.Error) action(error)
    return this
}

inline fun <T, R> AppResult<T>.mapOnSuccess(map: (T?) -> R): AppResult<R> = when (this) {
    is AppResult.Success -> AppResult.Success(map(result))
    is AppResult.Error -> this
}