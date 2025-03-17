package com.ufes.automobile.ui.common

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ufes.automobile.data.local.entity.MaintenanceEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A composable function that displays a card representing a maintenance record.
 *
 * This card shows the description, date, and cost of a maintenance operation.
 *
 * @param maintenance The [MaintenanceEntity] object containing the maintenance data.
 * @param modifier Optional [Modifier] to customize the card's appearance and layout.
 *
 * Example Usage:
 * ```
 * MaintenanceCard(
 *     maintenance = MaintenanceEntity(
 *         id = 1,
 *         description = "Oil Change",
 *         date = System.currentTimeMillis(),
 *         cost = 50.00
 *     ),
 *     modifier = Modifier.padding(16.dp)
 * )
 * ```
 */
@SuppressLint("DefaultLocale")
@Composable
fun MaintenanceCard(
    maintenance: MaintenanceEntity,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp, 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
                text = maintenance.description,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Date: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(maintenance.date))}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Cost: $${String.format("%.2f", maintenance.cost)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewMaintenanceCard() {
    MaintenanceCard(
        maintenance = MaintenanceEntity(
            id = 1,
            vehicleId = 1,
            description = "Oil Change",
            cost = 75.50f,
            date = System.currentTimeMillis() // Data atual como exemplo
        )
    )
}