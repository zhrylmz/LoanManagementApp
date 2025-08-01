package com.loanmanagementapp.features.home.data.service

import com.loanmanagementapp.features.home.data.model.LoanDTO
import retrofit2.Response

interface LoanService {
    suspend fun loadLoans(): Response<List<LoanDTO>>

    suspend fun saveLoans(loans: List<LoanDTO>)
}