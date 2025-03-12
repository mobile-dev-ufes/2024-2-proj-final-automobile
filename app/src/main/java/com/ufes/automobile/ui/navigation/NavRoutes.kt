package com.ufes.automobile.ui.navigation

/**
 * Sealed class representing the different navigation routes within the application.
 *
 * This sealed class defines all possible destinations within the application, ensuring
 * type-safe navigation and streamlined route management. Each route is represented as a
 * data object, inheriting from `Route` and carrying a unique string identifier.
 *
 * **Key Features:**
 * - **Type-Safety:** Prevents navigation to undefined routes at compile time.
 * - **Centralized Management:** All routes are defined in a single location, simplifying updates and maintenance.
 * - **String Representation:** Each route has a `route` property, which is the string used for navigation.
 * - **Parameterized Routes:** Supports routes with parameters, such as vehicle IDs.
 * - **Route Creation:** Provides utility functions (e.g., `createRoute`) for constructing parameterized route strings.
 *
 * **Usage:**
 * To navigate to a specific screen, refer to the corresponding data object (e.g., `Route.GarageScreen`).
 * For parameterized routes, use the `createRoute` function to build the correct route string.
 *
 * @property route The string representation of the route, used for navigation and matching.
 */
sealed class Route(val route: String) {
    data object GarageScreen : Route("garage")
    data object RegistryScreen : Route("registry")
    data object DashboardScreen : Route("dashboard/{vehicleId}") {
        fun createRoute(vehicleId: Int) = "dashboard/$vehicleId"
    }
    data object DisplacementScreen : Route("displacement/{vehicleId}") {
        fun createRoute(vehicleId: Int) = "displacement/$vehicleId"
    }
    data object MaintenanceScreen : Route("maintenance/{vehicleId}") {
        fun createRoute(vehicleId: Int) = "maintenance/$vehicleId"
    }
    data object RechargeScreen : Route("recharge/{vehicleId}") {
        fun createRoute(vehicleId: Int) = "recharge/$vehicleId"
    }
}