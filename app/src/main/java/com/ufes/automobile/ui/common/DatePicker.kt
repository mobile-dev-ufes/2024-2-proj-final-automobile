package com.ufes.automobile.ui.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ufes.automobile.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

/**
 * A composable function that provides a date picker field for selecting a date.
 *
 * This composable displays an outlined text field that, when clicked, opens a date picker dialog.
 * The selected date is then formatted and passed back via the `onPurchaseDateChange` callback.
 *
 * @param purchaseDate The currently selected date in "dd/MM/yyyy" format as a String.
 *                     An empty string indicates that no date is currently selected.
 * @param onPurchaseDateChange A callback function invoked when the user selects a new date.
 *                             It receives the newly selected date as a formatted string ("dd/MM/yyyy").
 * @param modifier Modifier for styling and layout customization of the underlying OutlinedTextField.
 * @param text The label text displayed above the text field.
 *
 * Example Usage:
 * ```
 * var selectedDate by remember { mutableStateOf("") }
 * DatePickerField(
 *     purchaseDate = selectedDate,
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    purchaseDate: String,
    onPurchaseDateChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    text: String
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = remember {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault()
        }
    }
    val selectedDate = remember(purchaseDate) {
        if (purchaseDate.isNotEmpty()) {
            dateFormatter.parse(purchaseDate)?.let { Calendar.getInstance().apply { time = it } }
        } else {
            Calendar.getInstance()
        } ?: Calendar.getInstance()
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate.timeInMillis
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    LaunchedEffect(isPressed) {
        if (isPressed) {
            showDatePicker = true
        }
    }

    OutlinedTextField(
        value = purchaseDate,
        onValueChange = {},
        label = {
            Text(
                text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier,
        readOnly = true,
        interactionSource = interactionSource,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                                timeInMillis = millis
                                set(Calendar.HOUR_OF_DAY, 0)
                                set(Calendar.MINUTE, 0)
                                set(Calendar.SECOND, 0)
                                set(Calendar.MILLISECOND, 0)
                            }
                            dateFormatter.timeZone = TimeZone.getTimeZone("UTC")
                            val date = calendar.time
                            onPurchaseDateChange(dateFormatter.format(date))
                            println("Selected millis: $millis, Formatted date: ${dateFormatter.format(date)}")
                        }
                        showDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text(stringResource(R.string.cancel)) }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}