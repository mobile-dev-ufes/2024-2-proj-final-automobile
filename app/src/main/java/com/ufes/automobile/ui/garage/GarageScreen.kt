package com.ufes.automobile.ui.garage

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ufes.automobile.ui.common.VehicleCard
import com.ufes.automobile.ui.navigation.Route


/**
 * [GarageScreen] composable function displays the user's garage, showing a list of vehicles.
 *
 * This screen fetches the list of vehicles from the [GarageViewModel] and displays them using
 * [VehicleCard]. It also provides a button to navigate to the vehicle registration screen.
 *
 * @param navController The navigation controller used for screen navigation.
 * @param viewModel The [GarageViewModel] used to fetch and manage vehicle data. Defaults to a
 *                  [hiltViewModel] instance.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarageScreen(
    navController: NavController,
    viewModel: GarageViewModel = hiltViewModel()
) {
    val vehicles by viewModel.vehicles.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Garage") }
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Route.RegistryScreen.route)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Vehicle")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            items(vehicles) { vehicle ->
                VehicleCard(
                    vehicle = vehicle,
                    onClick = {
                        navController.navigate(Route.DashboardScreen.createRoute(vehicle.id))
                    }
                )
            }
        }
    }
}