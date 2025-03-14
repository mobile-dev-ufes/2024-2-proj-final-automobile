package com.ufes.automobile.ui.maintenance

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.ui.common.parseDate
import com.ufes.automobile.ui.theme.AutoMobileTheme

@Composable
fun MaintenanceScreen(
    vehicleId: Int?,
    navController: NavController,
    viewModel: MaintenanceViewModel = hiltViewModel()
) {
    var description by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    MaintenanceContent(
        vehicleId = vehicleId,
        description = description,
        onDescriptionChange = { description = it },
        cost = cost,
        onCostChange = { cost = it },
        date = date,
        onDateChange = { date = it },
        onSaveClick = {
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
        isSaveEnabled = description.isNotBlank() && cost.isNotBlank() && date.isNotBlank(),
        onBackClick = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaintenanceContent(
    vehicleId: Int?,
    description: String,
    onDescriptionChange: (String) -> Unit,
    cost: String,
    onCostChange: (String) -> Unit,
    date: String,
    onDateChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    isSaveEnabled: Boolean,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Maintenance Registry") },
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
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = cost,
                onValueChange = { onCostChange(it.filter { char -> char.isDigit() || char == '.' }) },
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
fun MaintenanceContentPreview() {
    AutoMobileTheme {
        MaintenanceContent(
            vehicleId = 1,
            description = "Oil change",
            onDescriptionChange = {},
            cost = "50.0",
            onCostChange = {},
            date = "01/01/2023",
            onDateChange = {},
            onSaveClick = {},
            isSaveEnabled = true,
            onBackClick = {}
        )
    }
}