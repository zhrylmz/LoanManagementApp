package com.loanmanagementapp.contract.di

import com.loanmanagementapp.contract.json.JsonParser
import com.loanmanagementapp.contract.json.JsonParserManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface JsonModule {

    @Binds
    fun bindJson(jsonParser: JsonParserManager): JsonParser
}