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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.R
import com.ufes.automobile.domain.model.Vehicle
import com.ufes.automobile.ui.auth.AuthViewModel
import com.ufes.automobile.ui.common.VehicleCard
import com.ufes.automobile.ui.navigation.Route
import com.ufes.automobile.ui.theme.AutoMobileTheme


/**
 * [GarageScreen] is a composable function that displays the user's garage,
 * listing their registered vehicles. It provides functionality to navigate to
 * other screens like the vehicle registration screen, vehicle dashboard,
 * and login screen upon logout.
 *
 * @param navController The NavController instance used for navigating between
 *                      different screens in the application.
 * @param authViewModel The AuthViewModel instance, responsible for handling
 *                      authentication-related tasks, such as logout.
 * @param viewModel The GarageViewModel instance, responsible for managing
 *                  the list of vehicles and performing operations on them,
 *                  like fetching and deleting.
 */
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
                        text = stringResource(id = R.string.garage),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = stringResource(id = R.string.logout),
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
                    contentDescription = stringResource(id = R.string.add_vehicle),
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
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (vehicles.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.no_vehicles_added),
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
                                text = { Text(stringResource(R.string.delete_vehicle), color = MaterialTheme.colorScheme.error) },
                                onClick = {
                                    showMenu = false
                                    showDialog = true
                                }
                            )
                        }
                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text(stringResource(R.string.delete_vehicle)) },
                                text = { Text(stringResource(id = R.string.confirm_message, vehicle.brand, vehicle.model)) },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            onDeleteClick(vehicle)
                                            showDialog = false
                                        }
                                    ) {
                                        Text(stringResource(id = R.string.delete), color = MaterialTheme.colorScheme.error)
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDialog = false }) {
                                        Text(stringResource(id = R.string.cancel))
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