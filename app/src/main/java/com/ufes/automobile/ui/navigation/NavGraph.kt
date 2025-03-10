package com.ufes.automobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ufes.automobile.ui.dashboard.DashboardScreen
import com.ufes.automobile.ui.garage.GarageScreen
import com.ufes.automobile.ui.registry.RegistryScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.GarageScreen.route
    ) {
        composable(Route.GarageScreen.route) {
            GarageScreen(navController = navController)
        }
        composable(Route.RegistryScreen.route) {
            RegistryScreen(navController = navController)
        }
        composable(Route.DashboardScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toInt()
            DashboardScreen(vehicleId = vehicleId, navController = navController)
        }
    }
}