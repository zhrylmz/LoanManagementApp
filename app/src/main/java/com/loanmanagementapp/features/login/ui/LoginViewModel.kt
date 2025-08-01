package com.loanmanagementapp.features.login.ui

import androidx.lifecycle.ViewModel
import com.loanmanagementapp.common.annotation.EmailValidator
import com.loanmanagementapp.common.domain.TextFieldValidator
import com.loanmanagementapp.common.ui.component.LoanManagementTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @EmailValidator private val validator: TextFieldValidator<String>
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState(validator = validator))
    val uiState = _uiState.asStateFlow()

    fun onEmailStateChanged(textState: LoanManagementTextFieldState) {
        _uiState.update {
            it.copy(
                email = textState.text,
                isValidEmail = textState.isError.not()
            )
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }
}