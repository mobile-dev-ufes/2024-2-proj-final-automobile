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