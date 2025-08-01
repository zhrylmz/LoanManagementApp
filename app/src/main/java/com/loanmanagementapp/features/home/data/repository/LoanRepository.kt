package com.loanmanagementapp.features.home.data.repository

import com.loanmanagementapp.contract.model.AppResult
import com.loanmanagementapp.contract.model.mapOnSuccess
import com.loanmanagementapp.contract.qualifier.IoDispatcher
import com.loanmanagementapp.features.home.data.model.LoanDTO
import com.loanmanagementapp.features.home.data.service.LoanService
import com.loanmanagementapp.features.home.domain.model.Loan
import com.loanmanagementapp.features.home.domain.model.LoanStatus
import com.loanmanagementapp.features.home.domain.strategy.LoanEvolutionStrategy
import com.loanmanagementapp.features.home.domain.strategy.LoanEvolutionStrategyFactory
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
    val initialLoan = Loan(
        name = loan.name,
        principalAmount = loan.principalAmount,
        interestRate = loan.interestRate,
        status = LoanStatus.fromValue(loan.status),
        dueIn = loan.dueIn
    )
    val strategy: LoanEvolutionStrategy = LoanEvolutionStrategyFactory.forLoan(loan)
    strategy.evolve(initialLoan)
}.orEmpty()