package com.loanmanagementapp.common.di

import com.loanmanagementapp.common.annotation.EmailValidator
import com.loanmanagementapp.common.domain.EmailValidatorImpl
import com.loanmanagementapp.common.domain.TextFieldValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Provides
    @EmailValidator
    fun provideEmailValidator(): TextFieldValidator<String> = EmailValidatorImpl()
}