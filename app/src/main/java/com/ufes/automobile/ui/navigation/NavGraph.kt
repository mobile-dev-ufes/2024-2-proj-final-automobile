package com.ufes.automobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ufes.automobile.ui.dashboard.DashboardScreen
import com.ufes.automobile.ui.displacement.DisplacementScreen
import com.ufes.automobile.ui.garage.GarageScreen
import com.ufes.automobile.ui.maintenance.MaintenanceScreen
import com.ufes.automobile.ui.recharge.RechargeScreen
import com.ufes.automobile.ui.registry.RegistryScreen


/**
- * [NavGraph]
- *
- * Defines the navigation graph for the application.
- * It uses a [NavHost] to manage navigation between different screens.
- *
- * @param navController The [NavHostController] used to navigate between destinations.
- *
- * This function sets up the following routes:
- *   - [Route.GarageScreen]: Displays the Garage screen, showing a list of vehicles.
- *   - [Route.RegistryScreen]: Displays the Registry screen, likely for vehicle registration.
- *   - [Route.DashboardScreen]: Displays the Dashboard screen for a specific vehicle, identified by its ID.
- *     - It expects a `vehicleId` argument (an integer) to be passed in the route.
- *     - Example of how to navigate: `navController.navigate(Route.DashboardScreen.createRoute(vehicleId))`
- *
- * Each route's corresponding screen is defined in its own composable function (e.g., GarageScreen, RegistryScreen, DashboardScreen).
- */
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
        composable(Route.DisplacementScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toInt()
            DisplacementScreen(vehicleId = vehicleId, navController = navController)
        }
        composable(Route.MaintenanceScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toInt()
            MaintenanceScreen(vehicleId = vehicleId, navController = navController)
        }
        composable(Route.RechargeScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toInt()
            RechargeScreen(vehicleId = vehicleId, navController = navController)
        }
    }
}