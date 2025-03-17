package com.ufes.automobile.ui.registry

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ufes.automobile.R
import com.ufes.automobile.ui.common.DatePickerField
import com.ufes.automobile.ui.common.parseDate
import com.ufes.automobile.ui.theme.AutoMobileTheme

/**
 * RegistryScreen is a composable function that displays the vehicle registry screen.
 *
 * This screen allows users to input details about their vehicle, including brand, model,
 * manufacturing year, purchase date, whether it's electric, battery capacity (if electric),
 * range (if electric), and tank capacity (if not electric).
 *
 * It uses a [RegistryViewModel] to handle saving the vehicle data and interacts with a
 * [NavHostController] to manage navigation.
 *
 * @param navController The [NavHostController] used for navigating between screens.
 * @param viewModel The [RegistryViewModel] used for saving vehicle data. Defaults to a
 *                  [hiltViewModel] instance.
 */
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
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.vehicle_registration),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = brand,
                        onValueChange = onBrandChange,
                        label = {
                            Text(
                                text = stringResource(id = R.string.brand),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.directions_car),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        ),
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                    )
                    OutlinedTextField(
                        value = model,
                        onValueChange = onModelChange,
                        label = {
                            Text(
                                text = stringResource(id = R.string.model),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        ),
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
                    )
                    OutlinedTextField(
                        value = manufacturingYear,
                        onValueChange = { onManufacturingYearChange(it.filter { c -> c.isDigit() }) },
                        label = {
                            Text(
                                text = stringResource(id = R.string.manufacturing_year),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )

                    DatePickerField(
                        purchaseDate = purchaseDate,
                        onPurchaseDateChange = onPurchaseDateChange,
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.purchase_date)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.vehicle_type),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Switch(
                            checked = isElectric,
                            onCheckedChange = onIsElectricChange,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.primary,
                                checkedTrackColor = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.5f
                                ),
                                uncheckedThumbColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.6f
                                ),
                                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.3f
                                ),
                                checkedBorderColor = MaterialTheme.colorScheme.primary,
                                uncheckedBorderColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.5f
                                )
                            )
                        )
                        Text(
                            text = if (isElectric) stringResource(id = R.string.electric) else stringResource(id = R.string.combustion),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isElectric) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    if (isElectric) {
                        OutlinedTextField(
                            value = batteryCapacity,
                            onValueChange = { onBatteryCapacityChange(it.filter { char -> char.isDigit() || char == '.' }) },
                            label = {
                                Text(
                                    stringResource(id = R.string.battery_capacity),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    ImageVector.vectorResource(id = R.drawable.local_gas_station),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.5f
                                )
                            )
                        )
                        OutlinedTextField(
                            value = range,
                            onValueChange = { onRangeChange(it.filter { char -> char.isDigit() || char == '.' }) },
                            label = {
                                Text(
                                    stringResource(id = R.string.range),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    ImageVector.vectorResource(id = R.drawable.directions_car),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.5f
                                )
                            )
                        )
                    } else {
                        OutlinedTextField(
                            value = tankCapacity,
                            onValueChange = { onTankCapacityChange(it.filter { char -> char.isDigit() || char == '.' }) },
                            label = {
                                Text(
                                    stringResource(id = R.string.tank_capacity),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.local_gas_station),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.5f
                                )
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Button(
                        onClick = onSaveClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = isSaveEnabled,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSaveEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.3f
                            ),
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 6.dp,
                            pressedElevation = 8.dp
                        )
                    ) {
                        Text(
                            stringResource(id = R.string.save),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
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