package com.ufes.automobile.ui.garage

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.domain.model.Vehicle
import com.ufes.automobile.ui.auth.AuthViewModel
import com.ufes.automobile.ui.common.VehicleCard
import com.ufes.automobile.ui.navigation.Route
import com.ufes.automobile.ui.theme.AutoMobileTheme


@Composable
fun GarageScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel(),
    viewModel: GarageViewModel = hiltViewModel()
) {
    val vehicles by viewModel.vehicles.collectAsState()

    GarageContent(
        vehicles = vehicles,
        onAddClick = { navController.navigate(Route.RegistryScreen.route) },
        onVehicleClick = { vehicleId ->
            navController.navigate(Route.DashboardScreen.createRoute(vehicleId.toInt()))
        },
        onDeleteClick = {vehicle ->
            viewModel.deleteVehicle(vehicle)
        },
        onLogoutClick = {
            authViewModel.logout()
            navController.navigate(Route.LoginScreen.route)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarageContent(
    vehicles: List<Vehicle>,
    onAddClick: () -> Unit,
    onVehicleClick: (String) -> Unit,
    onDeleteClick: (Vehicle) -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Garage",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp),
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Vehicle",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background) // Simplifiquei a camada de fundo
        ) {
            if (vehicles.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No vehicles added yet.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 80.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(vehicles) { vehicle ->
                        var showMenu by remember { mutableStateOf(false) }
                        var showDialog by remember { mutableStateOf(false) }
                        VehicleCard(
                            vehicle = vehicle,
                            onClick = { onVehicleClick(vehicle.id.toString()) },
                            onLongPress = { showMenu = true },
                            modifier = Modifier.fillMaxWidth()
                        )
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                        ) {
                            DropdownMenuItem(
                                text = { Text("Delete Vehicle", color = MaterialTheme.colorScheme.error) },
                                onClick = {
                                    showMenu = false
                                    showDialog = true
                                }
                            )
                        }
                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text("Delete Vehicle") },
                                text = { Text("Are you sure you want to delete ${vehicle.brand} ${vehicle.model}?") },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            onDeleteClick(vehicle)
                                            showDialog = false
                                        }
                                    ) {
                                        Text("Delete", color = MaterialTheme.colorScheme.error)
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDialog = false }) {
                                        Text("Cancel")
                                    }
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun GarageContentPreview() {
    AutoMobileTheme {
        GarageContent(
            vehicles = listOf(
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
                Vehicle(
                    2,
                    "BYD",
                    "Dolphin Mini",
                    2025,
                    purchaseDate = 2025,
                    isElectric = true,
                    batteryCapacity = 38f,
                    range = 400f,
                    tankCapacity = null
                )
            ),
            onAddClick = {},
            onVehicleClick = {},
            onDeleteClick = {},
            onLogoutClick = {}
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Empty Garage")
@Composable
fun GarageContentEmptyPreview() {
    AutoMobileTheme {
        GarageContent(
            vehicles = emptyList(),
            onAddClick = {},
            onVehicleClick = {},
            onDeleteClick = {},
            onLogoutClick = {}
        )
    }
}