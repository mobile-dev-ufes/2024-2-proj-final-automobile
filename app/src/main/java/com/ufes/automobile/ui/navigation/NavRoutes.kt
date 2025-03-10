package com.ufes.automobile.ui.navigation

/**
 * Sealed class representing the different routes within the application.
 *
 * Each route is represented by a data object that inherits from this class and
 * specifies a unique route string. This allows for type-safe navigation and
 * simplifies route management.
 *
 * @property route The string representation of the route, used for navigation.
 */
sealed class Route(val route: String) {
    data object GarageScreen : Route("garage")
    data object RegistryScreen : Route("registry")
    data object DashboardScreen : Route("dashboard/{vehicleId}") {
        fun createRoute(vehicleId: Int): String {
            return "dashboard/$vehicleId"
        }
    }
}