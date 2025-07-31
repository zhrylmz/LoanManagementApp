package com.loanmanagementapp.features.home.di

import com.loanmanagementapp.features.home.data.service.LoanService
import com.loanmanagementapp.features.home.data.service.MockLoanService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HomeModule {

    @Binds
    fun provideLoanService(loanService: MockLoanService): LoanService
}
