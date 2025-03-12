package com.ufes.automobile.ui.displacement

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
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ufes.automobile.ui.common.parseDate


/**
 * Composable function that displays a screen for registering a vehicle displacement.
 *
 * This screen allows the user to input the distance, date, origin, and destination of a vehicle's displacement.
 * It also handles saving the displacement data using the provided [DisplacementViewModel].
 *
 * @param vehicleId The ID of the vehicle associated with the displacement. If null, the save operation will be skipped.
 * @param navController The NavController for managing navigation within the app.
 * @param viewModel The DisplacementViewModel used for saving displacement data. Defaults to a Hilt-provided ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplacementScreen(
    vehicleId: Int?,
    navController: NavController,
    viewModel: DisplacementViewModel = hiltViewModel()
) {
    var distance by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var origin by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Displacement Registry") },
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
                value = distance,
                onValueChange = { distance = it.filter { char -> char.isDigit() || char == '.' } },
                label = { Text("Distance (km)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date (dd/mm/aaaa)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = origin,
                onValueChange = { origin = it },
                label = { Text("Origin") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = destination,
                onValueChange = { destination = it },
                label = { Text("Destination") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    vehicleId?.let {
                        viewModel.saveDisplacement(
                            vehicleId = it,
                            distance = distance.toFloatOrNull() ?: 0f,
                            date = parseDate(date),
                            origin = origin,
                            destination = destination
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = distance.isNotBlank() && date.isNotBlank() && origin.isNotBlank() && destination.isNotBlank()
            ) {
                Text("Save")
            }
        }
    }
}

