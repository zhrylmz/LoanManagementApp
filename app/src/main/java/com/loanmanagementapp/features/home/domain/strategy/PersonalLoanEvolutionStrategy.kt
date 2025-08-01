package com.loanmanagementapp.features.home.domain.strategy

import com.loanmanagementapp.features.home.domain.model.Loan
import com.loanmanagementapp.features.home.domain.model.LoanStatus

class PersonalLoanEvolutionStrategy : LoanEvolutionStrategy {
    override fun evolve(loan: Loan): Loan {
        var newInterestRate = loan.interestRate
        var newStatus = loan.status
        var newDueIn = loan.dueIn
        val principalAmount = loan.principalAmount

        if (newStatus != LoanStatus.PAID && newStatus != LoanStatus.DEFAULT) {
            if (newDueIn > 0) {
                newInterestRate += 0.5
            }
        } else if (principalAmount < 1000) {
            newStatus = LoanStatus.PAID
        }

        if (newStatus == LoanStatus.OVERDUE && principalAmount > 5000) {
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
