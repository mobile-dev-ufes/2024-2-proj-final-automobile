package com.ufes.automobile.ui.navigation

sealed class Route(val route: String) {
    data object GarageScreen : Route("garage")
    data object RegistryScreen : Route("registry")
    data object DashboardScreen : Route("dashboard/{vehicleId}") {
        fun createRoute(vehicleId: Int): String {
            return "dashboard/$vehicleId"
        }
    }
}