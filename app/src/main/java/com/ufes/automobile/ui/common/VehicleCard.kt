package com.ufes.automobile.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ufes.automobile.domain.model.Vehicle

/**
 * Displays a card representing a vehicle, showing its details.
 *
 * @param vehicle The [Vehicle] data to be displayed.
 * @param onClick The action to be performed when the card is clicked.
 * @param modifier Modifier for styling and layout customization of the card.
 *
 * This composable function creates a card that presents information about a vehicle.
 * It displays the vehicle's brand and model, its manufacturing year, and whether it is electric or combustion.
 * If the vehicle is electric, it shows the autonomy; otherwise, it displays the tank capacity.
 * The card is clickable and triggers the provided `onClick` action when tapped.
 *
 * The card is styled using Material Design components and typography.
 *
 * Example Usage:
 * ```
 * VehicleCard(
 *     vehicle = Vehicle(brand = "Tesla", model = "Model S", manufacturingYear = 2023, isElectric = true, autonomy = 600),
 *     onClick = { println("Vehicle card clicked!") }
 * )
 * ```
 */
@Composable
fun VehicleCard(
    vehicle: Vehicle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "${vehicle.brand} ${vehicle.model}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Ano: ${vehicle.manufacturingYear}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = if (vehicle.isElectric) "Elétrico" else "Combustão",
                style = MaterialTheme.typography.bodySmall
            )
            if(vehicle.isElectric) {
                vehicle.autonomy?.let {
                    Text(
                        text = "Autonomia: $it km",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            } else {
                vehicle.tankCapacity?.let {
                    Text(
                        text = "Tanque: $it L",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}