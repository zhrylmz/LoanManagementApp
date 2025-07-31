package com.loanmanagementapp.features.home.data.model

import com.google.gson.annotations.SerializedName

data class LoanDTO(
    @SerializedName("name") val name: String,
    @SerializedName("principal_amount") val principalAmount: Double,
    @SerializedName("interest_rate") val interestRate: Double,
    @SerializedName("status") val status: String,
    @SerializedName("due_in") val dueIn: Int
)