package com.loanmanagementapp.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loanmanagementapp.features.home.domain.model.Loan

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, userName: String, onLogout: () -> Unit = {}) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Loan Management") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "User Name: $userName", fontSize = 16.sp)
            Spacer(Modifier.size(10.dp))
            if (uiState.loanList.isEmpty())
                Button(onClick = {
                    viewModel.updateLoans()
                }) {
                    Text("Load Loans", fontSize = 16.sp)
                }

            if (uiState.loanList.isNotEmpty()) {
                Button(onClick = {
                    onLogout()
                }) {
                    Text("Logout")
                }
                Text(text = "Loans:", fontSize = 16.sp)
                LoanList(uiState.loanList, onPerformCalculation = viewModel::onPerformCalculation)
            }
        }
    }
}

@Composable
fun LoanList(loans: List<Loan>, onPerformCalculation: (loan: Loan) -> Unit = {}) {
    LazyColumn {
        itemsIndexed(
            items = loans,
            key = { _, item -> item.name }
        ) { _, loan ->
            LaunchedEffect(loan) {
                onPerformCalculation(loan)
            }
            LoanItem(loan = loan)
        }
    }
}

@Composable
fun LoanItem(loan: Loan) {
    Column {
        Row {
            Text(
                "${loan.name} - ",
                fontSize = 16.sp
            )
            Text(
                "${loan.status} ",
                fontSize = 16.sp
            )
            Text(
                "Interest: ${loan.interestRate}%",
                fontSize = 16.sp
            )

        }
    }

    Spacer(modifier = Modifier.height(30.dp))
}
