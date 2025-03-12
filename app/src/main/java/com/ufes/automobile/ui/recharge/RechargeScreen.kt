package com.ufes.automobile.ui.recharge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.ui.common.parseDate

/**
 * Composable function for the Recharge Screen.
 *
 * This screen allows the user to register a recharge or refueling event for a vehicle.
 * It supports both electric and combustion vehicles, allowing the user to input the
 * amount, cost, and date of the event.
 *
 * @param vehicleId The ID of the vehicle for which the recharge/refueling is being registered.
 *                  If null, the recharge won't be saved and the save button will do nothing.
 * @param navController The NavController used for navigation within the app.
 * @param viewModel The RechargeViewModel responsible for handling the logic of the recharge.
 *                  It uses Hilt to inject the view model.
 */
@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isElectric) "Recharge Registry" else "Refueling Registry") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                    onCheckedChange = { isElectric = it }
                )
                Text(if (isElectric) "Electric" else "Combustion")
            }
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it.filter { char -> char.isDigit() || char == '.' } },
                label = { Text(if (isElectric) "Amount (kWh)" else "Amount (L)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cost,
                onValueChange = { cost = it.filter { char -> char.isDigit() || char == '.' } },
                label = { Text("Cost ($)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date (dd/mm/aaaa)") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
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
                modifier = Modifier.fillMaxWidth(),
                enabled = amount.isNotBlank() && cost.isNotBlank() && date.isNotBlank()
            ) {
                Text("Save")
            }
        }
    }
}