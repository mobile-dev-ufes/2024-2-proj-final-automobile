package com.ufes.automobile.ui.garage

import android.content.res.Configuration
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.domain.model.Vehicle
import com.ufes.automobile.ui.common.VehicleCard
import com.ufes.automobile.ui.navigation.Route
import com.ufes.automobile.ui.theme.AutoMobileTheme


@Composable
fun GarageScreen(
    navController: NavController,
    viewModel: GarageViewModel = hiltViewModel()
) {
    val vehicles by viewModel.vehicles.collectAsState()

    GarageContent(
        vehicles = vehicles,
        onAddClick = { navController.navigate(Route.RegistryScreen.route) },
        onVehicleClick = { vehicleId ->
            navController.navigate(Route.DashboardScreen.createRoute(vehicleId.toInt()))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarageContent(
    vehicles: List<Vehicle>,
    onAddClick: () -> Unit,
    onVehicleClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Garage") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Vehicle",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            items(vehicles) { vehicle ->
                VehicleCard(
                    vehicle = vehicle,
                    onClick = { onVehicleClick(vehicle.id.toString()) }
                )
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
            onVehicleClick = {}
        )
    }
}