package com.loanmanagementapp.features.home.ui

import com.loanmanagementapp.features.home.domain.model.Loan
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val loanList: PersistentList<Loan> = persistentListOf()
)