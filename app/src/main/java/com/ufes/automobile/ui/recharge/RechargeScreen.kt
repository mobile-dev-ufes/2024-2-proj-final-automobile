package com.ufes.automobile.ui.recharge

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.R
import com.ufes.automobile.ui.common.DatePickerField
import com.ufes.automobile.ui.common.parseDate
import com.ufes.automobile.ui.theme.AutoMobileTheme

/**
 * Composable function that displays the recharge screen, allowing users to input details
 * about a vehicle recharge event and save it.
 *
 * @param vehicleId The ID of the vehicle being recharged. If null, it implies an error state or invalid vehicle.
 * @param navController The NavController used for navigating between screens.
 * @param viewModel The RechargeViewModel responsible for managing recharge data and operations.
 *                  Defaults to a viewModel provided by Hilt.
 *
 * The screen allows the user to input:
 *  - Amount: The quantity of fuel/electricity added.
 *  - Cost: The total cost of the recharge.
 *  - Date: The date of the recharge event.
 *  - Description: Any additional notes about the recharge.
 *  - isElectric: A boolean indicating if the vehicle is electric or not
 *
 * The user can:
 * - Save the recharge information.
 * - Navigate back to the previous screen.
 *
 * The save operation is enabled only when:
 * - Amount is not empty.
 * - Cost is not empty.
 * - Description is not empty.
 * - Date is not empty
 *
 * Upon successful save, the screen navigates back using the NavController.
 *
 * The function uses state hoisting to manage the input fields.
 * It leverages the RechargeContent composable to display the UI elements.
 */
@Composable
fun RechargeScreen(
    vehicleId: Int?,
    navController: NavController,
    viewModel: RechargeViewModel = hiltViewModel()
) {
    var amount by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isElectric by remember { mutableStateOf(false) }

    RechargeContent(
        amount = amount,
        onAmountChange = { amount = it },
        cost = cost,
        onCostChange = { cost = it },
        date = date,
        onDateChange = { date = it },
        isElectric = isElectric,
        onIsElectricChange = { isElectric = it },
        description = description,
        onDescriptionChange = { description = it },
        onSaveClick = {
            vehicleId?.let {
                viewModel.saveRecharge(
                    vehicleId = it,
                    isElectric = isElectric,
                    amount = amount.toFloatOrNull() ?: 0f,
                    cost = cost.toFloatOrNull() ?: 0f,
                    date = parseDate(date),
                    description = description
                )
                navController.popBackStack()
            }
        },
        isSaveEnabled = amount.isNotBlank() && cost.isNotBlank() && description.isNotBlank() && date.isNotBlank(),
        onBackClick = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RechargeContent(
    amount: String,
    onAmountChange: (String) -> Unit,
    cost: String,
    onCostChange: (String) -> Unit,
    date: String,
    onDateChange: (String) -> Unit,
    isElectric: Boolean,
    onIsElectricChange: (Boolean) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    isSaveEnabled: Boolean,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isElectric) stringResource(id = R.string.recharging_registry) else stringResource(id = R.string.refueling_registry),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(id = R.string.type),
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
                                checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                uncheckedThumbColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                            )
                        )
                        Text(
                            text = if (isElectric) stringResource(id = R.string.electric) else stringResource(id = R.string.combustion),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isElectric) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    OutlinedTextField(
                        value = amount,
                        onValueChange = onAmountChange,
                        label = {
                            Text(
                                if (isElectric) stringResource(id = R.string.amount_kwh) else stringResource(id = R.string.amount_l),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = if(isElectric) ImageVector.vectorResource(id = R.drawable.bolt) else ImageVector.vectorResource(id = R.drawable.local_gas_station),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = onDescriptionChange,
                        label = {
                            Text(
                                stringResource(id = R.string.description),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.description),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                    OutlinedTextField(
                        value = cost,
                        onValueChange = onCostChange,
                        label = {
                            Text(
                                stringResource(id = R.string.cost),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.attach_money),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                    DatePickerField(
                        purchaseDate = date,
                        onPurchaseDateChange = onDateChange,
                        modifier = Modifier.fillMaxWidth(),
                        stringResource(id = R.string.date)
                    )
                }
            }
            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = isSaveEnabled,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSaveEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
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

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun RechargeContentPreview() {
    AutoMobileTheme {
        RechargeContent(
            amount = "50.0",
            onAmountChange = {},
            cost = "75.50",
            onCostChange = {},
            date = "01/01/2023",
            onDateChange = {},
            isElectric = false,
            onIsElectricChange = {},
            description = "At the gas station near the mall",
            onDescriptionChange = {},
            onSaveClick = {},
            isSaveEnabled = true,
            onBackClick = {}
        )
    }
}