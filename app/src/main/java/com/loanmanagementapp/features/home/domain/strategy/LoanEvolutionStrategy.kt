package com.loanmanagementapp.features.home.domain.strategy

import com.loanmanagementapp.features.home.domain.model.Loan

interface LoanEvolutionStrategy {
    fun evolve(loan: Loan): Loan
}
