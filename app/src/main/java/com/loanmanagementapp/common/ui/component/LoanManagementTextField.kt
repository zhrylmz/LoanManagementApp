package com.loanmanagementapp.common.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.loanmanagementapp.common.domain.TextFieldValidator

@Composable
fun ValidatedTextField(
    label: String,
    modifier: Modifier = Modifier,
    validator: TextFieldValidator<String>,
    errorMessage: String = "Invalid input",
    onValueChange: (LoanManagementTextFieldState) -> Unit = {},
    value: String,
) {
    var text by remember { mutableStateOf(value) }
    var isError by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                isError = !validator.validate(it)
                onValueChange(LoanManagementTextFieldState(text = it, isError = isError))
            },
            label = { Text(label) },
            isError = isError,
            modifier = Modifier.fillMaxWidth()
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

data class LoanManagementTextFieldState(
    val text: String = "",
    val isError: Boolean = false,
)
