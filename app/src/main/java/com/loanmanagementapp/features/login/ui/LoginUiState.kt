package com.loanmanagementapp.features.login.ui

import com.loanmanagementapp.common.domain.TextFieldValidator

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isValidEmail: Boolean = false,
    val validator: TextFieldValidator<String>
)