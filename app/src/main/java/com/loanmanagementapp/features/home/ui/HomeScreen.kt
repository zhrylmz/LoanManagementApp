package com.loanmanagementapp.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.loanmanagementapp.common.ui.component.PrimaryButton
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

            PrimaryButton(
                text = "Logout",
                onClick = { onLogout() },
                modifier = Modifier
            )

            Spacer(Modifier.size(10.dp))

            if (uiState.loanList.isEmpty()) {
                PrimaryButton(
                    text = "Load Loans",
                    onClick = { viewModel.updateLoans() },
                    modifier = Modifier
                )
            } else {
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = loan.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = loan.displayStatus,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = loan.statusColor(),
                    modifier = Modifier
                        .background(loan.statusColor().copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Interest: ${loan.interestRate}%",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}
