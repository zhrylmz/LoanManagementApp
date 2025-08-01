package com.loanmanagementapp.features.home.domain.model

data class Loan(
    val name: String,
    val principalAmount: Double,
    val interestRate: Double,
    val status: LoanStatus,
    val dueIn: Int
)