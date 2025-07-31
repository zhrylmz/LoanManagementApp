package com.loanmanagementapp.common.domain

interface TextFieldValidator<T> {
    val errorMessage: String
    fun validate(candidate: T?): Boolean
}