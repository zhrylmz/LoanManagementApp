package com.loanmanagementapp.features.home.ui

import app.cash.turbine.test
import com.loanmanagementapp.contract.model.AppResult
import com.loanmanagementapp.features.home.domain.model.Loan
import com.loanmanagementapp.features.home.domain.model.LoanStatus
import com.loanmanagementapp.features.home.domain.usecase.PerformLoanCalculationUseCase
import com.loanmanagementapp.features.home.domain.usecase.UpdateLoansUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @MockK
    private lateinit var updateLoansUseCase: UpdateLoansUseCase

    @MockK(relaxed = true)
    private lateinit var performLoanCalculationUseCase: PerformLoanCalculationUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = HomeViewModel(updateLoansUseCase, performLoanCalculationUseCase)
    }

    @Test
    fun updateLoans_updates_uiState_with_loans_on_success() = runTest {
        val loans = listOf(
            Loan(
                name = "Loan 1",
                principalAmount = 1000.0,
                interestRate = 5.5,
                status = LoanStatus.PAID,
                dueIn = 10
            ),
            Loan(
                name = "Loan 2",
                principalAmount = 2000.0,
                interestRate = 3.2,
                status = LoanStatus.DEFAULT,
                dueIn = 20
            )
        )

        coEvery { updateLoansUseCase.invoke() } returns AppResult.Success(loans)

        viewModel.updateLoans()
        advanceUntilIdle()

        viewModel.uiState.test {
            val item = awaitItem()

            assertEquals(loans, item.loanList)
        }
    }

    @Test
    fun onPerformCalculation_invokes_use_case_with_given_loan() = runTest {
        val loan = Loan(
            name = "Loan 3",
            principalAmount = 3500.0,
            interestRate = 7.0,
            status = LoanStatus.OVERDUE,
            dueIn = 15
        )
        viewModel.onPerformCalculation(loan)

        advanceUntilIdle()

        coVerify(exactly = 1) { performLoanCalculationUseCase.invoke(loan) }
    }
}
