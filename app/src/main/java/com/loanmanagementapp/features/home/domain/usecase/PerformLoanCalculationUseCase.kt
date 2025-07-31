package com.loanmanagementapp.features.home.domain.usecase

import com.loanmanagementapp.contract.qualifier.DefaultDispatcher
import com.loanmanagementapp.features.home.domain.model.Loan
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.sqrt

class PerformLoanCalculationUseCase @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun invoke(loan: Loan) = withContext(dispatcher) {
        // some mock operations that tires CPU
        repeat(1000) { _ -> sqrt(loan.interestRate) }
        loan.principalAmount * loan.interestRate
    }
}