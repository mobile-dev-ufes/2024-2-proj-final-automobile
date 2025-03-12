package com.ufes.automobile.ui.maintenance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.ui.common.parseDate

/**
 * [MaintenanceScreen] is a composable function that provides a UI for registering maintenance records.
 *
 * It allows the user to input the description, cost, and date of a maintenance operation.
 * When the user clicks the "Save" button, the maintenance record is saved using the [MaintenanceViewModel].
 *
 * @param vehicleId The ID of the vehicle for which the maintenance is being recorded. If null, no maintenance is saved.
 * @param navController The [NavController] used for navigating back to the previous screen after saving.
 * @param viewModel The [MaintenanceViewModel] used for saving the maintenance data. It's injected using Hilt.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaintenanceScreen(
    vehicleId: Int?,
    navController: NavController,
    viewModel: MaintenanceViewModel = hiltViewModel()
) {
    var description by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Maintenance Registry") },
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
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
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
                        viewModel.saveMaintenance(
                            vehicleId = it,
                            description = description,
                            cost = cost.toFloatOrNull() ?: 0f,
                            date = parseDate(date)
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = description.isNotBlank() && cost.isNotBlank() && date.isNotBlank()
            ) {
                Text("Save")
            }
        }
    }
}