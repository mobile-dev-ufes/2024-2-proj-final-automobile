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
import java.util.Calendar

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
                label = { Text("Purchase Date (dd/mm/yyyy") },
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