package com.loanmanagementapp.features.home.domain.strategy

import com.loanmanagementapp.features.home.domain.model.Loan
import com.loanmanagementapp.features.home.domain.model.LoanStatus

class AutoLoanEvolutionStrategy : LoanEvolutionStrategy {
    override fun evolve(loan: Loan): Loan {
        var newInterestRate = loan.interestRate
        var newStatus = loan.status
        var newDueIn = loan.dueIn
        val principalAmount = loan.principalAmount

        // Dummy: auto loans get decreasing rate and cannot be PAID early
        if (newStatus != LoanStatus.PAID && newStatus != LoanStatus.DEFAULT) {
            if (newDueIn > 0) {
                newInterestRate -= 0.2
                if (newInterestRate < 0.0) newInterestRate = 0.0
            }
        }
        // Just a variation, don't set PAID for principal < 1000 on auto loans

        if (newStatus == LoanStatus.OVERDUE && principalAmount > 3000) {
            newStatus = LoanStatus.DEFAULT
        }

        newDueIn -= 1

        if (newDueIn < 0) {
            newStatus = if (newStatus != LoanStatus.PAID) {
                if (principalAmount > 0) LoanStatus.OVERDUE else LoanStatus.DEFAULT
            } else {
                LoanStatus.PAID
            }
        }

        return loan.copy(
            interestRate = newInterestRate,
            status = newStatus,
            dueIn = newDueIn
        )
    }
}
