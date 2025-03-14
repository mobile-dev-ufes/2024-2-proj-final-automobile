package com.ufes.automobile.ui.registry

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ufes.automobile.ui.common.parseDate
import com.ufes.automobile.ui.theme.AutoMobileTheme

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

    RegistryContent(
        brand = brand,
        onBrandChange = { brand = it },
        model = model,
        onModelChange = { model = it },
        manufacturingYear = manufacturingYear,
        onManufacturingYearChange = { manufacturingYear = it },
        purchaseDate = purchaseDate,
        onPurchaseDateChange = { purchaseDate = it },
        isElectric = isElectric,
        onIsElectricChange = { isElectric = it },
        batteryCapacity = batteryCapacity,
        onBatteryCapacityChange = { batteryCapacity = it },
        range = range,
        onRangeChange = { range = it },
        tankCapacity = tankCapacity,
        onTankCapacityChange = { tankCapacity = it },
        onSaveClick = {
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
        onBackClick = { navController.popBackStack() },
        isSaveEnabled = brand.isNotBlank() && model.isNotBlank() && manufacturingYear.isNotBlank() && purchaseDate.isNotBlank()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistryContent(
    brand: String,
    onBrandChange: (String) -> Unit,
    model: String,
    onModelChange: (String) -> Unit,
    manufacturingYear: String,
    onManufacturingYearChange: (String) -> Unit,
    purchaseDate: String,
    onPurchaseDateChange: (String) -> Unit,
    isElectric: Boolean,
    onIsElectricChange: (Boolean) -> Unit,
    batteryCapacity: String,
    onBatteryCapacityChange: (String) -> Unit,
    range: String,
    onRangeChange: (String) -> Unit,
    tankCapacity: String,
    onTankCapacityChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    isSaveEnabled: Boolean
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Vehicle Registry") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
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
                onValueChange = onBrandChange,
                label = { Text("Brand") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = model,
                onValueChange = onModelChange,
                label = { Text("Model") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = manufacturingYear,
                onValueChange = { onManufacturingYearChange(it.filter { char -> char.isDigit() }) },
                label = { Text("Manufacturing Year") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = purchaseDate,
                onValueChange = onPurchaseDateChange,
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
                    onCheckedChange = onIsElectricChange
                )
                Text(if (isElectric) "Electric" else "Combustion")
            }
            if (isElectric) {
                OutlinedTextField(
                    value = batteryCapacity,
                    onValueChange = { onBatteryCapacityChange(it.filter { char -> char.isDigit() || char == '.' }) },
                    label = { Text("Battery Capacity (kWh)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = range,
                    onValueChange = { onRangeChange(it.filter { char -> char.isDigit() || char == '.' }) },
                    label = { Text("Range (km)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                OutlinedTextField(
                    value = tankCapacity,
                    onValueChange = { onTankCapacityChange(it.filter { char -> char.isDigit() || char == '.' }) },
                    label = { Text("Tank Capacity (L)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = onSaveClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = isSaveEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Text("Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun RegistryContentPreview() {
    AutoMobileTheme {
        RegistryContent(
            brand = "Toyota",
            onBrandChange = {},
            model = "Corolla",
            onModelChange = {},
            manufacturingYear = "2023",
            onManufacturingYearChange = {},
            purchaseDate = "01/01/2023",
            onPurchaseDateChange = {},
            isElectric = false,
            onIsElectricChange = {},
            batteryCapacity = "",
            onBatteryCapacityChange = {},
            range = "",
            onRangeChange = {},
            tankCapacity = "50.0",
            onTankCapacityChange = {},
            onSaveClick = {},
            onBackClick = {},
            isSaveEnabled = true
        )
    }
}