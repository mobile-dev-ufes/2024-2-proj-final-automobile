package com.ufes.automobile.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ufes.automobile.ui.accident.AccidentScreen
import com.ufes.automobile.ui.auth.LoginScreen
import com.ufes.automobile.ui.dashboard.DashboardScreen
import com.ufes.automobile.ui.displacement.DisplacementScreen
import com.ufes.automobile.ui.garage.GarageScreen
import com.ufes.automobile.ui.insurance.InsuranceScreen
import com.ufes.automobile.ui.maintenance.MaintenanceReminderScreen
import com.ufes.automobile.ui.maintenance.MaintenanceScreen
import com.ufes.automobile.ui.recharge.RechargeScreen
import com.ufes.automobile.ui.registry.RegistryScreen
import com.ufes.automobile.ui.reports.ReportsScreen

/**
 * Defines the navigation graph for the application.
 *
 * This composable function sets up the navigation structure using Jetpack Compose Navigation.
 * It defines the different screens (destinations) within the app and how they are connected.
 * It also applies custom transitions (slide-in/out) for a smoother user experience.
 *
 * @param navController The NavHostController that manages the navigation within the graph.
 * @param startDestination The initial destination (screen) to display when the navigation graph is first shown.
 */
@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(300)
            )
        }
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
        composable(Route.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Route.AccidentScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toInt()
            AccidentScreen(vehicleId = vehicleId, navController = navController)
        }
        composable(Route.InsuranceScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toInt()
            InsuranceScreen(vehicleId = vehicleId, navController = navController)
        }
        composable(Route.MaintenanceReminderScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toInt()
            MaintenanceReminderScreen(vehicleId = vehicleId, navController = navController)
        }
        composable(Route.ReportsScreen.route) { backStackEntry ->
            val vehicleId = backStackEntry.arguments?.getString("vehicleId")?.toInt()
            ReportsScreen(vehicleId = vehicleId, navController = navController)
        }
    }
}

/*
            enterTransition = {
                fadeIn(animationSpec = tween(300)) // Fade in para entrada
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) // Fade out para saída
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300)) // Fade in para voltar
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300)) // Fade out para voltar
            }
 */