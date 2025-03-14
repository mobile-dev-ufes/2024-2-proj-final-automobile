package com.ufes.automobile.ui.recharge

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.ui.common.parseDate
import com.ufes.automobile.ui.theme.AutoMobileTheme

@Composable
fun RechargeScreen(
    vehicleId: Int?,
    navController: NavController,
    viewModel: RechargeViewModel = hiltViewModel()
) {
    var amount by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var isElectric by remember { mutableStateOf(false) }

    RechargeContent(
        vehicleId = vehicleId,
        amount = amount,
        onAmountChange = { amount = it },
        cost = cost,
        onCostChange = { cost = it },
        date = date,
        onDateChange = { date = it },
        isElectric = isElectric,
        onIsElectricChange = { isElectric = it },
        onSaveClick = {
            vehicleId?.let {
                viewModel.saveRecharge(
                    vehicleId = it,
                    isElectric = isElectric,
                    amount = amount.toFloatOrNull() ?: 0f,
                    cost = cost.toFloatOrNull() ?: 0f,
                    date = parseDate(date)
                )
                navController.popBackStack()
            }
        },
        isSaveEnabled = amount.isNotBlank() && cost.isNotBlank() && date.isNotBlank(),
        onBackClick = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RechargeContent(
    vehicleId: Int?,
    amount: String,
    onAmountChange: (String) -> Unit,
    cost: String,
    onCostChange: (String) -> Unit,
    date: String,
    onDateChange: (String) -> Unit,
    isElectric: Boolean,
    onIsElectricChange: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
    isSaveEnabled: Boolean,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isElectric) "Recharge Registry" else "Refueling Registry") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Type")
                Switch(
                    checked = isElectric,
                    onCheckedChange = onIsElectricChange
                )
                Text(if (isElectric) "Electric" else "Combustion")
            }
            OutlinedTextField(
                value = amount,
                onValueChange = onAmountChange,
                label = { Text(if (isElectric) "Amount (kWh)" else "Amount (L)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cost,
                onValueChange = onCostChange,
                label = { Text("Cost ($)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = date,
                onValueChange = onDateChange,
                label = { Text("Date (dd/mm/aaaa)") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = isSaveEnabled
            ) {
                Text("Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun RechargeContentPreview() {
    AutoMobileTheme {
        RechargeContent(
            vehicleId = 1,
            amount = "50.0",
            onAmountChange = {},
            cost = "75.50",
            onCostChange = {},
            date = "01/01/2023",
            onDateChange = {},
            isElectric = false,
            onIsElectricChange = {},
            onSaveClick = {},
            isSaveEnabled = true,
            onBackClick = {}
        )
    }
}