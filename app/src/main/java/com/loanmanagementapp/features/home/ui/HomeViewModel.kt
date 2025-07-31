package com.loanmanagementapp.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loanmanagementapp.contract.model.onSuccess
import com.loanmanagementapp.features.home.domain.model.Loan
import com.loanmanagementapp.features.home.domain.usecase.PerformLoanCalculationUseCase
import com.loanmanagementapp.features.home.domain.usecase.UpdateLoansUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val updateLoansUseCase: UpdateLoansUseCase,
    private val performCalculationForLoan: PerformLoanCalculationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun updateLoans() {
        viewModelScope.launch {
            updateLoansUseCase.invoke().onSuccess { loans ->
                _uiState.update { it.copy(loanList = loans.toPersistentList()) }
            }
        }
    }

    fun onPerformCalculation(loan: Loan) {
        viewModelScope.launch {
            performCalculationForLoan.invoke(loan)
        }
    }
}