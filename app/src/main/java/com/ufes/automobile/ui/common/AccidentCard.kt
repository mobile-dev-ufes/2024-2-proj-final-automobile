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
import com.ufes.automobile.data.local.entity.AccidentEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Displays an accident's details in a styled card.
 *
 * This composable function renders a card that presents the essential information
 * of an accident, including its description, date, and location. It leverages
 * Material Design components to ensure a visually appealing and consistent user
 * interface.
 *
 * @param accident The [AccidentEntity] object representing the accident. This object
 *                 must contain `description`, `date` (as a Long timestamp), and
 *                 `location` properties.
 * @param modifier An optional [Modifier] to customize the card's layout, appearance,
 *                 and behavior. This allows you to apply additional styling or
 *                 constraints specific to the card's placement in your layout.
 *                 Defaults to an empty [Modifier].
 *
 * Example Usage:
 * ```
 * AccidentCard(
 *     accident = AccidentEntity(
 *         id = 1,
 *         description = "Minor collision on Main Street",
 *         date = System.currentTimeMillis(),
 *         location = "123 Main Street, Anytown"
 *     ),
 *     modifier = Modifier.padding(16.dp)
 * )
 * ```
 */
@SuppressLint("DefaultLocale")
@Composable
fun AccidentCard(
    accident: AccidentEntity,
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
                text = accident.description,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Date: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(accident.date))}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Location: ${accident.location}",
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
fun PreviewAccidentCard() {
    AccidentCard(
        accident = AccidentEntity(
            id = 1,
            vehicleId = 1,
            date = System.currentTimeMillis(), // Data atual como exemplo
            description = "Collision with another vehicle",
            location = "Main Street, Downtown"
        )
    )
}