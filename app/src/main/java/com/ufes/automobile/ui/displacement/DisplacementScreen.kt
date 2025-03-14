package com.ufes.automobile.ui.displacement

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ufes.automobile.ui.common.parseDate
import com.ufes.automobile.ui.theme.AutoMobileTheme

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

    DisplacementContent(
        vehicleId = vehicleId,
        distance = distance,
        onDistanceChange = { distance = it },
        date = date,
        onDateChange = { date = it },
        origin = origin,
        onOriginChange = { origin = it },
        destination = destination,
        onDestinationChange = { destination = it },
        onSaveClick = {
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
        isSaveEnabled = distance.isNotBlank() && date.isNotBlank() && origin.isNotBlank() && destination.isNotBlank(),
        onBackClick = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplacementContent(
    vehicleId: Int?,
    distance: String,
    onDistanceChange: (String) -> Unit,
    date: String,
    onDateChange: (String) -> Unit,
    origin: String,
    onOriginChange: (String) -> Unit,
    destination: String,
    onDestinationChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    isSaveEnabled: Boolean,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Displacement Registry") },
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
            OutlinedTextField(
                value = distance,
                onValueChange = { onDistanceChange(it.filter { char -> char.isDigit() || char == '.' }) },
                label = { Text("Distance (km)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = date,
                onValueChange = onDateChange,
                label = { Text("Date (dd/mm/aaaa)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = origin,
                onValueChange = onOriginChange,
                label = { Text("Origin") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = destination,
                onValueChange = onDestinationChange,
                label = { Text("Destination") },
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
fun DisplacementContentPreview() {
    AutoMobileTheme {
        DisplacementContent(
            vehicleId = 1,
            distance = "150.5",
            onDistanceChange = {},
            date = "01/01/2023",
            onDateChange = {},
            origin = "São Paulo",
            onOriginChange = {},
            destination = "Rio de Janeiro",
            onDestinationChange = {},
            onSaveClick = {},
            isSaveEnabled = true,
            onBackClick = {}
        )
    }
}