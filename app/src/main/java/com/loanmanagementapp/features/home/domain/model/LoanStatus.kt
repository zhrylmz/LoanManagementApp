package com.loanmanagementapp.features.home.domain.model

enum class LoanStatus(val value: String) {
    PAID("paid"),
    DEFAULT("default"),
    OVERDUE("overdue");

    companion object {
        fun fromValue(value: String): LoanStatus {
            return entries.find { it.value == value } ?: DEFAULT
        }
    }
}