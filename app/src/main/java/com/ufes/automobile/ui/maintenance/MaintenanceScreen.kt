package com.ufes.automobile.ui.maintenance

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
 * [MaintenanceScreen] is a composable function that provides a screen for adding or editing maintenance records.
 *
 * This screen allows the user to input the description, cost, and date of a maintenance event
 * and then save it. It also handles navigation back to the previous screen.
 *
 * @param vehicleId The ID of the vehicle to which the maintenance record will be associated.
 *                  If null, it might indicate an error or a scenario where a vehicle hasn't been selected.
 *                  Should be a valid vehicle id when adding a new record
 * @param navController The [NavController] used for navigating between screens.
 * @param viewModel The [MaintenanceViewModel] responsible for handling the business logic related to maintenance operations.
 *                  It defaults to a viewModel provided by hilt.
 *
 * @Usage:
 *  - Call this function within your composable hierarchy to render the maintenance screen.
 *  - Ensure that the `vehicleId` is provided if a maintenance record needs to be linked to a specific vehicle.
 *  - The `navController` should be properly set up for navigation.
 *
 * @Functionality:
 *   - Manages the state of the description, cost, and date inputs.
 *   - Calls the `saveMaintenance` function in the `MaintenanceViewModel` when the user clicks the save button.
 *   - Navigates back to the previous screen using `navController.popBackStack()` after saving or when the user clicks the back button.
 *   - Validates that the description, cost, and date fields are not empty before enabling the save button.
 *   - Handles parsing the date string to a [java.time.LocalDate] object (implementation details in the `parseDate` function, assumed to be present).
 *   - Handles converting cost to Float, default to 0f if it is not a number.
 *   - Uses the [MaintenanceContent] composable to build the layout.
 */
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
                title = {
                    Text(
                        stringResource(id = R.string.maintenance_registry),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            stringResource(id = R.string.back),
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
                    modifier = Modifier.padding(16.dp)
                ) {
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
                                imageVector = Icons.Default.Build,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                    OutlinedTextField(
                        value = cost,
                        onValueChange = { onCostChange(it.filter { char -> char.isDigit() || char == '.' }) },
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
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
fun MaintenanceContentPreview() {
    AutoMobileTheme {
        MaintenanceContent(
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