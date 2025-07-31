package com.loanmanagementapp.features.home.data.service

import android.content.Context
import com.loanmanagementapp.contract.json.JsonParser
import com.loanmanagementapp.contract.qualifier.IoDispatcher
import com.loanmanagementapp.features.home.data.model.LoanDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

private const val JSON_FILE = "loans.json"

class MockLoanService @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val dispatcher: CoroutineDispatcher,
    private val jsonParser: JsonParser
) : LoanService {

    override suspend fun loadLoans(): Response<List<LoanDTO>> = withContext(dispatcher) {
        try {
            val jsonString =
                context.assets.open(JSON_FILE).bufferedReader().use { it.readText() }
            val loans = jsonParser.fromJsonToArrayList(json = jsonString, LoanDTO::class)
            return@withContext Response.success(loans)
        } catch (e: IOException) {
            Response.error(400, e.message.orEmpty().toResponseBody())
        }
    }

    override suspend fun saveLoans(loans: List<LoanDTO>) {
        println("Loans saved: $loans")
    }
}