package com.loanmanagementapp.features.home.data.repository

import com.loanmanagementapp.contract.model.AppResult
import com.loanmanagementapp.contract.model.mapOnSuccess
import com.loanmanagementapp.contract.qualifier.IoDispatcher
import com.loanmanagementapp.features.home.data.model.LoanDTO
import com.loanmanagementapp.features.home.data.service.LoanService
import com.loanmanagementapp.features.home.domain.model.Loan
import com.loanmanagementapp.features.home.domain.model.LoanStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoanRepository @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val loanService: LoanService,
) {
    suspend fun loadLoans(): AppResult<List<Loan>> = withContext(dispatcher) {
        request { loanService.loadLoans() }.mapOnSuccess {
            it.toMap()
        }
    }

    suspend fun saveLoans(loans: List<LoanDTO>) = withContext(dispatcher) {
        loanService.saveLoans(loans)
    }
}

internal fun List<LoanDTO>?.toMap(): List<Loan> = this?.map { loan ->
    var newStatus = LoanStatus.fromValue(loan.status)

    var newInterestRate = loan.interestRate
    var newDueIn = loan.dueIn
    val principalAmount = loan.principalAmount

    if (newStatus != LoanStatus.PAID && newStatus != LoanStatus.DEFAULT) {
        if (newDueIn > 0) {
            newInterestRate += 0.5 // Faiz oranı her gün artıyor
        }
    } else {
        if (principalAmount < 1000) {
            newStatus = LoanStatus.PAID
        }
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

    Loan(
        name = loan.name,
        principalAmount = principalAmount,
        interestRate = newInterestRate,
        status = newStatus,
        dueIn = newDueIn
    )
}.orEmpty()