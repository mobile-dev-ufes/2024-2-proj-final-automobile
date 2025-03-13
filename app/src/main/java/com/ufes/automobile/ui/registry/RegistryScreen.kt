package com.ufes.automobile.ui.registry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.navigation.NavHostController
import com.ufes.automobile.ui.common.parseDate
import java.util.Calendar

/**
 * RegistryScreen Composable function.
 *
 * This screen allows the user to register a new vehicle by providing its details.
 * It includes fields for brand, model, manufacturing year, purchase date, vehicle type (electric/combustion),
 * battery capacity/tank capacity, and range.
 *
 * @param navController The NavHostController used for navigation.
 * @param viewModel The RegistryViewModel instance used for handling vehicle data. Defaults to a Hilt-provided instance.
 *
 * The function uses the following states:
 * - `brand`: The brand of the vehicle (String).
 * - `model`: The model of the vehicle (String).
 * - `manufacturingYear`: The manufacturing year of the vehicle (String, digits only).
 * - `purchaseDate`: The purchase date of the vehicle (String, format: dd/mm/yyyy).
 * - `isElectric`: A boolean */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistryScreen(
    navController: NavHostController,
    viewModel: RegistryViewModel = hiltViewModel()
    ) {
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var manufacturingYear by remember { mutableStateOf("") }
    var purchaseDate by remember { mutableStateOf("") }
    var isElectric by remember { mutableStateOf(false) }
    var batteryCapacity by remember { mutableStateOf("") }
    var range by remember { mutableStateOf("") }
    var tankCapacity by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Vehicle Registry") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = brand,
                onValueChange = { brand = it },
                label = { Text("Brand") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = model,
                onValueChange = { model = it },
                label = { Text("Model") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = manufacturingYear,
                onValueChange = { manufacturingYear = it.filter { char -> char.isDigit() } },
                label = { Text("Manufacturing Year") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = purchaseDate,
                onValueChange = { purchaseDate = it },
                label = { Text("Purchase Date (dd/mm/yyyy)") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Vehicle Type")
                Switch(
                    checked = isElectric,
                    onCheckedChange = { isElectric = it }
                )
                Text(if (isElectric) "Electric" else "Combustion")
            }
            if (isElectric) {
                OutlinedTextField(
                    value = batteryCapacity,
                    onValueChange = { batteryCapacity = it.filter { char -> char.isDigit() || char == '.' } },
                    label = { Text("Battery Capacity (kWh)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = range,
                    onValueChange = { range = it.filter { char -> char.isDigit() || char == '.' } },
                    label = { Text("Range (km)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                OutlinedTextField(
                    value = tankCapacity,
                    onValueChange = { tankCapacity = it.filter { char -> char.isDigit() || char == '.' } },
                    label = { Text("Tank Capacity (L)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = {
                    viewModel.saveVehicle(
                        brand = brand,
                        model = model,
                        manufacturingYear = manufacturingYear.toIntOrNull() ?: 0,
                        purchaseDate = parseDate(purchaseDate),
                        isElectric = isElectric,
                        batteryCapacity = batteryCapacity.toFloatOrNull(),
                        range = range.toFloatOrNull(),
                        tankCapacity = tankCapacity.toFloatOrNull()
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = brand.isNotBlank() && model.isNotBlank() && manufacturingYear.isNotBlank() && purchaseDate.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Save")
            }
        }
    }
}