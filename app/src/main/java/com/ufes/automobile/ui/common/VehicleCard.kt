package com.ufes.automobile.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ufes.automobile.R
import com.ufes.automobile.domain.model.Vehicle

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VehicleCard(
    vehicle: Vehicle,
    onClick: () -> Unit,
    onLongPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongPress,
            )
            .padding(8.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (vehicle.isElectric) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${vehicle.brand} ${vehicle.model} (${vehicle.manufacturingYear})",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (vehicle.isElectric) stringResource(id = R.string.type_electric) else stringResource(id = R.string.type_combustion),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (vehicle.isElectric) {
                vehicle.range?.let {
                    Text(
                        text = stringResource(id = R.string.range_km, it),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                vehicle.tankCapacity?.let {
                    Text(
//                        text = "Tank Capacity: $it L",
                        text = stringResource(id = R.string.tank_capacity_l, it),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewVehicleCard() {
    VehicleCard(
        vehicle = Vehicle(
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
        onClick = {},
        onLongPress = {}
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewVehicleCard2() {
    VehicleCard(
        vehicle = Vehicle(
            1,
            "BYD",
            "Dolphin",
            2024,
            purchaseDate = 2024,
            isElectric = true,
            batteryCapacity = 40f,
            range = 300f,
            tankCapacity = null
        ),
        onClick = {},
        onLongPress = {}
    )
}