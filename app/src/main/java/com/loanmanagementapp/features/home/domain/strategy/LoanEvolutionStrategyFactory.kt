package com.loanmanagementapp.features.home.domain.strategy

import com.loanmanagementapp.features.home.data.model.LoanDTO

object LoanEvolutionStrategyFactory {
    fun forLoan(loan: LoanDTO): LoanEvolutionStrategy =
        if (loan.name.contains("Auto", ignoreCase = true)) {
            AutoLoanEvolutionStrategy()
        } else {
            PersonalLoanEvolutionStrategy()
        }
}
