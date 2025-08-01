package com.loanmanagementapp.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object GsonModule {
    @Provides
    fun buildWithTimeZone(): Gson {
        return GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .create()
    }
}
