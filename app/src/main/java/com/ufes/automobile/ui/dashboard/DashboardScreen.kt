package com.ufes.automobile.ui.dashboard

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.sharp.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.R
import com.ufes.automobile.domain.model.Vehicle
import com.ufes.automobile.ui.navigation.Route
import com.ufes.automobile.ui.theme.AutoMobileTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Composable function that represents the Dashboard screen.
 *
 * This screen displays information about a specific vehicle and provides navigation to other related screens,
 * such as Recharge, Displacement, Maintenance, Reports, Accident, Insurance and Maintenance Reminder.
 *
 * @param vehicleId The ID of the vehicle to display. If null, no vehicle will be loaded.
 * @param navController The NavController used for navigating between screens.
 * @param viewModel The DashboardViewModel used to manage the state and data of the dashboard. Defaults to a hiltViewModel.
 *
 * Usage:
 * ```
 *  DashboardScreen(
 *      vehicleId = 1,
 *      navController = navController
 *  )
 * ```
 *
 * Functionality:
 * - Loads the vehicle data based on the provided `vehicleId` using the `DashboardViewModel`.
 * - Displays the vehicle information using the `DashboardContent` composable.
 * - Handles navigation to the following screens when their respective buttons are clicked:
 *   - Recharge: Navigates to the Recharge screen, passing the vehicle ID.
 *   - Displacement: Navigates to the Displacement screen, passing the vehicle ID.
 *   - Maintenance: Navigates to the Maintenance screen, passing the vehicle ID.
 *   - Reports: Navigates to the Reports screen, passing the vehicle ID.
 *   - Accident: Navigates to the Accident screen, passing the vehicle ID.
 *   - Insurance: Navigates to the Insurance screen, passing the vehicle ID.
 *   - Maintenance Reminder: Navigates to the Maintenance Reminder screen, passing the vehicle ID.
 *   - Back: Pops the back stack to return to the previous screen.
 *
 * State Management:
 * - Uses `LaunchedEffect` to load the vehicle data when `vehicleId` changes.
 * - Uses `collectAsState` to observe the `vehicle` state from the `DashboardViewModel`.
 *
 * Note:
 * The `Route` class (e.g., `Route.RechargeScreen`) is assumed to define the navigation routes and how to create them with parameters.
 */
@Composable
fun DashboardScreen(
    vehicleId: Int?,
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    LaunchedEffect(vehicleId) {
        vehicleId?.let { viewModel.loadVehicle(it) }
    }

    val vehicle = viewModel.vehicle.collectAsState().value

    DashboardContent(
        vehicle = vehicle,
        onRechargeClick = {
            navController.navigate(
                Route.RechargeScreen.createRoute(
                    vehicle?.id ?: 0
                )
            )
        },
        onDisplacementClick = {
            navController.navigate(
                Route.DisplacementScreen.createRoute(
                    vehicle?.id ?: 0
                )
            )
        },
        onMaintenanceClick = {
            navController.navigate(
                Route.MaintenanceScreen.createRoute(
                    vehicle?.id ?: 0
                )
            )
        },
        onReportsClick = {
            navController.navigate(
                Route.ReportsScreen.createRoute(
                    vehicle?.id ?: 0
                )
            )
        },
        onAccidentClick = {
            navController.navigate(
                Route.AccidentScreen.createRoute(
                    vehicle?.id ?: 0
                )
            )
        },
        onInsuranceClick = {
            navController.navigate(
                Route.InsuranceScreen.createRoute(
                    vehicle?.id ?: 0
                )
            )
        },
        onMaintenanceReminderClick = {
            navController.navigate(
                Route.MaintenanceReminderScreen.createRoute(
                    vehicle?.id ?: 0
                )
            )
        },
        onBackClick = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    vehicle: Vehicle?,
    onRechargeClick: () -> Unit,
    onDisplacementClick: () -> Unit,
    onMaintenanceClick: () -> Unit,
    onReportsClick: () -> Unit,
    onAccidentClick: () -> Unit,
    onInsuranceClick: () -> Unit,
    onMaintenanceReminderClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.vehicle_dashboard),
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
        if (vehicle != null) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "${vehicle.brand} ${vehicle.model}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            stringResource(R.string.year, vehicle.manufacturingYear),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        val typeText = if (vehicle.isElectric) stringResource(id = R.string.electric) else stringResource(id = R.string.combustion)
                        Text(
                            text = stringResource(id = R.string.vehicle_type, typeText),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = stringResource(id = R.string.purchase_date_dashboard, SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                                Date(vehicle.purchaseDate)
                            )),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (vehicle.isElectric) {
                            vehicle.batteryCapacity?.let {
                                Text(
                                    text = stringResource(id = R.string.battery_capacity_dashboard, it),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            vehicle.range?.let {
                                Text(
                                    text = stringResource(id = R.string.range_dashboard, it),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        } else {
                            vehicle.tankCapacity?.let {
                                Text(
                                    text = stringResource(id = R.string.tank_capacity_dashboard, it),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = onRechargeClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp,
                                pressedElevation = 8.dp
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (vehicle.isElectric) ImageVector.vectorResource(
                                        id = R.drawable.bolt
                                    ) else ImageVector.vectorResource(id = R.drawable.local_gas_station),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = if (vehicle.isElectric) stringResource(id = R.string.charge) else stringResource(id = R.string.refuel),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        Button(
                            onClick = onDisplacementClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp,
                                pressedElevation = 8.dp
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.directions_car),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.displacement),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = onMaintenanceClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.onTertiary
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp,
                                pressedElevation = 8.dp
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.car_repair),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                )
                                Text(
                                    text = stringResource(id = R.string.maintenance),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        Button(
                            onClick = onInsuranceClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp,
                                pressedElevation = 8.dp
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.shield),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.insurance),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = onAccidentClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = MaterialTheme.colorScheme.onError
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp,
                                pressedElevation = 8.dp
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Sharp.Warning,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.accident),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        Button(
                            onClick = onMaintenanceReminderClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp,
                                pressedElevation = 8.dp
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.event),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                                Text(
                                    text = stringResource(id = R.string.maintenance_reminder),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium,
                                    lineHeight = 17.sp,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = onReportsClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp,
                                pressedElevation = 8.dp
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.query_stats),
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.reports),
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DashboardContentCombustionPreview() {
    AutoMobileTheme {
        DashboardContent(
            Vehicle(
                1,
                "Toyota",
                "Corolla",
                2024,
                purchaseDate = 2024,
                isElectric = false,
                batteryCapacity = null,
                range = null,
                tankCapacity = 50f
            ),
            onRechargeClick = {},
            onDisplacementClick = {},
            onMaintenanceClick = {},
            onReportsClick = {},
            onAccidentClick = {},
            onInsuranceClick = {},
            onMaintenanceReminderClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DashboardContentElectricPreview() {
    AutoMobileTheme {
        DashboardContent(
            Vehicle(
                1,
                "BYD",
                "Dolphin",
                2024,
                purchaseDate = 2024,
                isElectric = true,
                batteryCapacity = 40f,
                range = 400f,
                tankCapacity = null
            ),
            onRechargeClick = {},
            onDisplacementClick = {},
            onMaintenanceClick = {},
            onReportsClick = {},
            onAccidentClick = {},
            onInsuranceClick = {},
            onMaintenanceReminderClick = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun DashboardContentLoadingPreview() {
    AutoMobileTheme {
        DashboardContent(
            vehicle = null,
            onRechargeClick = {},
            onDisplacementClick = {},
            onMaintenanceClick = {},
            onReportsClick = {},
            onAccidentClick = {},
            onInsuranceClick = {},
            onMaintenanceReminderClick = {},
            onBackClick = {}
        )
    }
}