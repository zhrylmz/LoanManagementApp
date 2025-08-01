package com.loanmanagementapp.features.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loanmanagementapp.common.ui.component.PrimaryButton
import com.loanmanagementapp.common.ui.component.ValidatedTextField

@Composable
fun LoginScreen(viewModel: LoginViewModel, onLoginSuccess: (String) -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ValidatedTextField(
            value = uiState.email,
            label = "Username",
            validator = uiState.validator,
            onValueChange = { textState ->
                viewModel.onEmailStateChanged(textState)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        PrimaryButton(
            text = "Login",
            onClick = { onLoginSuccess.invoke(uiState.email) },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.isValidEmail
        )
    }
}