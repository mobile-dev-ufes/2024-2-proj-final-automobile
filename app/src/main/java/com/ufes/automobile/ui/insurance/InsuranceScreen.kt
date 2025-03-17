package com.ufes.automobile.ui.insurance

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.ui.theme.AutoMobileTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ufes.automobile.R
import com.ufes.automobile.ui.common.DatePickerField
import com.ufes.automobile.ui.common.parseDate

/**
 * [InsuranceScreen] is a composable function that displays the insurance details screen.
 * It allows the user to input and save insurance information for a specific vehicle.
 *
 * @param navController The navigation controller used to navigate between screens.
 * @param vehicleId The ID of the vehicle for which the insurance details are being entered.
 *                  If null, it means no vehicle is associated, but the screen could still be displayed for other use cases (e.g., adding insurance without a vehicle).
 * @param viewModel The [InsuranceViewModel] used to interact with the insurance data layer.
 */
@Composable
fun InsuranceScreen(
    navController: NavController,
    vehicleId: Int?,
    viewModel: InsuranceViewModel = hiltViewModel()
) {
    var insurer by remember { mutableStateOf("") }
    var policyNumber by remember { mutableStateOf("") }
    var assistanceDetails by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }

    InsuranceContent(
        insurer = insurer,
        onInsurerChange = { insurer = it },
        policyNumber = policyNumber,
        onPolicyNumberChange = { policyNumber = it },
        assistanceDetails = assistanceDetails,
        onAssistanceDetailsChange = { assistanceDetails = it },
        startDate = startDate,
        onStartDateChange = { startDate = it },
        endDate = endDate,
        onEndDateChange = { endDate = it },
        cost = cost,
        onCostChange = { cost = it },
        onSaveClick = {
            vehicleId?.let {
                viewModel.saveInsurance(
                    vehicleId = it,
                    insurer = insurer,
                    policyNumber = policyNumber,
                    assistanceDetails = assistanceDetails, // Add comma here
                    startDate = parseDate(startDate),
                    endDate = parseDate(endDate),
                    cost = cost.toFloatOrNull() ?: 0f
                )
                navController.popBackStack()
            }
        },
        onBackClick = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsuranceContent(
    insurer: String,
    onInsurerChange: (String) -> Unit,
    policyNumber: String,
    onPolicyNumberChange: (String) -> Unit,
    assistanceDetails: String,
    onAssistanceDetailsChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    startDate: String,
    onStartDateChange: (String) -> Unit,
    endDate: String,
    onEndDateChange: (String) -> Unit,
    cost: String,
    onCostChange: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.register_insurance),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
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
                    OutlinedTextField(
                        value = insurer,
                        onValueChange = onInsurerChange,
                        label = {
                            Text(
                                stringResource(R.string.insurer),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.shield),
                                contentDescription = stringResource(R.string.insurer_icon),
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
                        value = policyNumber,
                        onValueChange = onPolicyNumberChange,
                        label = {
                            Text(
                                stringResource(R.string.policy_number),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = stringResource(R.string.policy_number_icon),
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
                        value = assistanceDetails,
                        onValueChange = onAssistanceDetailsChange,
                        label = {
                            Text(
                                stringResource(R.string.assistance_details),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.description),
                                contentDescription = stringResource(R.string.assistance_details_icon),
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
                    DatePickerField(
                        purchaseDate = startDate,
                        onPurchaseDateChange = onStartDateChange,
                        modifier = Modifier.fillMaxWidth(),
                        stringResource(R.string.start_date)
                    )
                    DatePickerField(
                        purchaseDate = endDate,
                        onPurchaseDateChange = onEndDateChange,
                        modifier = Modifier.fillMaxWidth(),
                        stringResource(R.string.end_date)
                    )
                    OutlinedTextField(
                        value = cost,
                        onValueChange = { onCostChange(it.filter { char -> char.isDigit() || char == '.' }) },
                        label = {
                            Text(
                                stringResource(R.string.monthly_cost),
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
                }
            }

            Button(
                onClick = onSaveClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = insurer.isNotBlank() && policyNumber.isNotBlank() && assistanceDetails.isNotBlank() && startDate.isNotBlank() && endDate.isNotBlank() && cost.isNotBlank(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (insurer.isNotBlank() && policyNumber.isNotBlank() && assistanceDetails.isNotBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp
                )
            ) {

                    Text(
                        stringResource(R.string.save),
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
fun InsuranceRegistrationContentPreview() {
    AutoMobileTheme {
        InsuranceContent(
            insurer = "Allianz",
            onInsurerChange = {},
            policyNumber = "123456",
            onPolicyNumberChange = {},
            assistanceDetails = "Telephone: 27 98899 2002, Insurance Company Fulano da Silva.",
            onAssistanceDetailsChange = {},
            onSaveClick = {},
            onBackClick = {},
            startDate = "01/01/2023",
            onStartDateChange = {},
            endDate = "01/01/2023",
            onEndDateChange = {},
            cost = "160.00",
            onCostChange = {}
        )
    }
}