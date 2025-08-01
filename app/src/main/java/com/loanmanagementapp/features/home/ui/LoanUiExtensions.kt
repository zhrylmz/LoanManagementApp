package com.loanmanagementapp.features.home.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import com.loanmanagementapp.features.home.domain.model.Loan
import com.loanmanagementapp.features.home.domain.model.LoanStatus
import java.util.Locale

val Loan.displayStatus: String
    get() = status.value.replaceFirstChar { it.titlecase(Locale.getDefault()) }

@Composable
fun Loan.statusColor(): Color = when (status) {
    LoanStatus.PAID -> Color(0xFF2E7D32)
    LoanStatus.OVERDUE -> Color(0xFFC62828)
    else -> MaterialTheme.colorScheme.primary
}
