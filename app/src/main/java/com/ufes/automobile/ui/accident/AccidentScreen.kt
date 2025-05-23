package com.ufes.automobile.ui.accident

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.ui.theme.AutoMobileTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ufes.automobile.ui.common.parseDate
import com.ufes.automobile.R
import com.ufes.automobile.ui.common.DatePickerField

/**
 * [AccidentScreen] is a composable function responsible for displaying the screen
 * where users can input and save details about an accident.
 *
 * @param navController The NavController used for navigating between screens.
 * @param vehicleId The ID of the vehicle associated with the accident. Can be null if not applicable.
 * @param viewModel The [AccidentViewModel] responsible for handling the business logic related to accidents.
 *                  Uses Hilt for dependency injection.
 *
 * This screen allows users to:
 *   - Input the date of the accident.
 *   - Input a description of the accident.
 *   - Input the location of the accident.
 *   - Save the accident details.
 *   - Navigate back to the previous screen.
 *
 * When the user clicks the "Save" button:
 *   - The `vehicleId`, `description`, `location`, and `date` are passed to the [AccidentViewModel]'s `saveAccident` method.
 *   - The date string is parsed into a Date object using the `parseDate` function (assumed to be defined elsewhere).
 *   - After saving, the screen navigates back to the previous screen using `navController.popBackStack()`.
 *
 * When the user clicks the "Back" button:
 *   - The screen navigates back to the previous screen using `navController.popBackStack()`.
 *
 * The screen uses state variables (`accidentDate`, `description`, `location`) to track the user's input.
 * These are updated using the provided callbacks (`onAccidentDateChange`, `onDescriptionChange`, `onLocationChange`)
 * passed to the underlying `AccidentContent` composable.
 *
 * The screen UI is rendered by the `AccidentContent` composable (assumed to be defined elsewhere).
 */
@Composable
fun AccidentScreen(
    navController: NavController,
    vehicleId: Int?,
    viewModel: AccidentViewModel = hiltViewModel()
) {
    var accidentDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    AccidentContent(
        accidentDate = accidentDate,
        onAccidentDateChange = { accidentDate = it },
        description = description,
        onDescriptionChange = { description = it },
        location = location,
        onLocationChange = { location = it },
        onSaveClick = {
            vehicleId?.let {
                viewModel.saveAccident(
                    vehicleId = it,
                    description = description,
                    location = location,
                    date = parseDate(accidentDate)
                )
                navController.popBackStack()
            }
        },
        onBackClick = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccidentContent(
    accidentDate: String,
    onAccidentDateChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.register_accident),
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
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DatePickerField(
                        purchaseDate = accidentDate,
                        onPurchaseDateChange = onAccidentDateChange,
                        modifier = Modifier.fillMaxWidth(),
                        stringResource(id = R.string.accident_date)
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
                                contentDescription = stringResource(id = R.string.description_icon),
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
                    OutlinedTextField(
                        value = location,
                        onValueChange = onLocationChange,
                        label = {
                            Text(
                                stringResource(id = R.string.location),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = stringResource(id = R.string.location_icon),
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
                enabled = accidentDate.isNotBlank() && description.isNotBlank() && location.isNotBlank(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (accidentDate.isNotBlank() && description.isNotBlank() && location.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
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
                    text = stringResource(id = R.string.save),
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
fun AccidentRegistrationContentPreview() {
    AutoMobileTheme {
        AccidentContent(
            accidentDate = "20/05/2025",
            onAccidentDateChange = {},
            description = "The driver collided with the rear of the car",
            onDescriptionChange = {},
            location = "Avenida Paulista, 1000",
            onLocationChange = {},
            onSaveClick = {},
            onBackClick = {}
        )
    }
}

