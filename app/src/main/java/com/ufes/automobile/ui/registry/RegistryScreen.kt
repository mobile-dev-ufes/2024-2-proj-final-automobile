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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar

/**
 * RegistryScreen Composable function.
 *
 * This screen allows the user to register a new vehicle by providing its details.
 * It includes fields for brand, model, manufacturing year, purchase date, vehicle type (electric/combustion),
 * battery capacity/tank capacity, and autonomy.
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
    var autonomy by remember { mutableStateOf("") }
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
                    value = autonomy,
                    onValueChange = { autonomy = it.filter { char -> char.isDigit() || char == '.' } },
                    label = { Text("Autonomy (km)") },
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
                        autonomy = autonomy.toFloatOrNull(),
                        tankCapacity = tankCapacity.toFloatOrNull()
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = brand.isNotBlank() && model.isNotBlank() && manufacturingYear.isNotBlank() && purchaseDate.isNotBlank()
            ) {
                Text("Save")
            }
        }
    }
}

/**
 * Parses a date string in "dd/MM/yyyy" format and returns its corresponding timestamp (milliseconds since epoch).
 *
 * If the input string is not in the correct format or if any of the date components are invalid,
 * it returns the current timestamp as a fallback.
 *
 * @param dateStr The date string to parse, expected in "dd/MM/yyyy" format.
 * @return The timestamp (milliseconds since epoch) representing the parsed date,
 *         or the current timestamp if the date string is invalid.
 *
 * Example:
 * ```
 * val timestamp = parseDate("25/12/2023") // Returns the timestamp for December 25th, 2023
 * val currentTimestamp = parseDate("invalid-date") // Returns the current timestamp
 * ```
 *
 * @throws NumberFormatException if any of the day, month, or year components cannot be parsed as integers. In reality, this will not happen because `toIntOrNull` will return null in such cases, causing default values to be used.
 * @throws IllegalArgumentException If a date component is outside the valid range (for example, an invalid month) the Calendar object will handle it by wrapping to the next/previous month/year, so no exception will be thrown.
 *
 */
private fun parseDate(dateStr: String): Long {
    val parts = dateStr.split("/")
    if (parts.size == 3) {
        val day = parts[0].toIntOrNull() ?: 1
        val month = parts[1].toIntOrNull() ?: 1
        val year = parts[2].toIntOrNull() ?: 2024
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        return calendar.timeInMillis
    }
    return System.currentTimeMillis() // Retorna data atual como fallback
}
