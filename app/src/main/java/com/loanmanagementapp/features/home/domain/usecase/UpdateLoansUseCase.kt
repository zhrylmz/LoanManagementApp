package com.loanmanagementapp.features.home.domain.usecase

import com.loanmanagementapp.contract.model.AppResult
import com.loanmanagementapp.contract.model.onSuccess
import com.loanmanagementapp.contract.qualifier.DefaultDispatcher
import com.loanmanagementapp.features.home.data.model.LoanDTO
import com.loanmanagementapp.features.home.data.repository.LoanRepository
import com.loanmanagementapp.features.home.domain.model.Loan
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateLoansUseCase @Inject constructor(
    private val repository: LoanRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun invoke(): AppResult<List<Loan>> = withContext(dispatcher) {
        val loans = repository.loadLoans()

        loans.onSuccess { loan ->
            val updatedLoans = loan.map {
                LoanDTO(
                    name = it.name,
                    principalAmount = it.principalAmount,
                    interestRate = it.interestRate,
                    status = it.status.value,
                    dueIn = it.dueIn
                )
            }

            repository.saveLoans(updatedLoans)
        }
        return@withContext loans
    }
}