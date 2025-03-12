package com.ufes.automobile.ui.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.ui.navigation.Route
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Composable function representing the Dashboard screen of the application.
 *
 * This screen displays information about a selected vehicle and provides navigation
 * to other screens related to vehicle management, such as registering recharges/refuels,
 * registering distance traveled, registering maintenance, and viewing reports.
 *
 * @param vehicleId The ID of the vehicle to display information for. If null, it is assumed that there is no vehicle to display.
 * @param navController The NavController used for navigating to other screens.
 * @param viewModel The DashboardViewModel used to manage the vehicle data. It defaults to a Hilt provided ViewModel.
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
             TopAppBar(
                 title = { Text(text = "Vehicle Dashboard") },
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
    ) {  paddingValues ->
        if (vehicle != null) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "${vehicle.brand} ${vehicle.model}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Year: ${vehicle.manufacturingYear}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Type: ${if (vehicle.isElectric) "Electric" else "Combustion"}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Purchase date: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                                Date(vehicle.purchaseDate)
                            )}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (vehicle.isElectric) {
                            vehicle.batteryCapacity?.let {
                                Text(
                                    text = "Battery Capacity: $it kWh",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            vehicle.range?.let {
                                Text(
                                    text = "Range: $it km",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } else {
                            vehicle.tankCapacity?.let {
                                Text(
                                    text = "Tank Capacity: $it L",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { navController.navigate(Route.RechargeScreen.createRoute(vehicle.id)) },
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                    ) {
                        Text(if (vehicle.isElectric) "Register Recharge" else "Registry Refuel")
                    }

                    Button(
                        onClick = { navController.navigate(Route.DisplacementScreen.createRoute(vehicle.id)) },
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                    ) {
                        Text("Register Distance Travelled")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { navController.navigate(Route.MaintenanceScreen.createRoute(vehicle.id)) },
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                    ) {
                        Text("Register Maintenance")
                    }

                    Button(
                        onClick = { navController.navigate(Route.GarageScreen.route) },
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                    ) {
                        Text("See Reports")
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}