package com.ufes.automobile.ui.maintenance

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.ui.theme.AutoMobileTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ufes.automobile.R
import com.ufes.automobile.ui.common.DatePickerField
import com.ufes.automobile.ui.common.parseDate

/**
 * [MaintenanceReminderScreen] is a composable function responsible for displaying the screen
 * where users can input and save a maintenance reminder for a specific vehicle.
 *
 * @param navController The NavController instance used for navigating between screens.
 * @param vehicleId The ID of the vehicle for which the maintenance reminder is being created.
 *                  If null, no reminder will be saved.
 * @param viewModel The [MaintenanceReminderViewModel] responsible for managing the state
 *                  and logic related to maintenance reminders. It defaults to an instance
 *                  provided by Hilt's `hiltViewModel()`.
 *
 * This composable maintains the state of the maintenance date and description using `remember`
 * and `mutableStateOf`. It then utilizes the `MaintenanceReminderContent` composable to render
 * the UI and handle user input.
 *
 * When the user clicks "Save", the function calls the `saveMaintenanceReminder` method of the
 * `viewModel`, passing the `vehicleId`, description and parsed maintenance date.
 * After saving the data it navigates back to the previous screen.
 *
 * When the user clicks the back button, the function pops the back stack, navigating to the
 * previous screen.
 *
 * The [MaintenanceReminderContent] composable is responsible for rendering the UI elements
 * (e.g., text fields, buttons) and calling the provided callbacks when events occur.
 *
 *  Example usage:
 *  ```
 *  MaintenanceReminderScreen(
 *      navController = myNavController,
 *      vehicleId = 123
 *  )
 *  ```
 */
@Composable
fun MaintenanceReminderScreen(
    navController: NavController,
    vehicleId: Int?,
    viewModel: MaintenanceReminderViewModel = hiltViewModel()
) {
    var maintenanceDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    MaintenanceReminderContent(
        maintenanceDate = maintenanceDate,
        onMaintenanceDateChange = { maintenanceDate = it },
        description = description,
        onDescriptionChange = { description = it },
        onSaveClick = {
            vehicleId?.let {
                viewModel.saveMaintenanceReminder(
                    vehicleId = it,
                    description = description,
                    reminderDate = parseDate(maintenanceDate)
                )
                navController.popBackStack()
            }
        },
        onBackClick = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaintenanceReminderContent(
    maintenanceDate: String,
    onMaintenanceDateChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.register_maintenance_reminder),
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
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DatePickerField(
                        purchaseDate = maintenanceDate,
                        onPurchaseDateChange = onMaintenanceDateChange,
                        modifier = Modifier.fillMaxWidth(),
                        stringResource(id = R.string.maintenance_date),
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        maxLines = 4,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                }
            }

            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = maintenanceDate.isNotBlank() && description.isNotBlank(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (maintenanceDate.isNotBlank() && description.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
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

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MaintenanceReminderContentPreview() {
    AutoMobileTheme {
        MaintenanceReminderContent(
            maintenanceDate = "20/02/2027",
            onMaintenanceDateChange = {},
            description = "Change the air conditioning filter.",
            onDescriptionChange = {},
            onSaveClick = {},
            onBackClick = {}
        )
    }
}