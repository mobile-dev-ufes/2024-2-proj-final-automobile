package com.ufes.automobile.ui.common

import java.util.Calendar

/**
 * Parses a date string in "dd/MM/yyyy" format and returns its corresponding timestamp (milliseconds since epoch).
 *
 * If the input string is not in the correct format or if any of the date components are invalid,
 * it returns the current timestamp as a fallback.
 *
 * @param dateStr The date string to parse, expected in "dd/MM/yyyy" format.
 * @return The timestamp (milliseconds since epoch) representing the parsed date,
 *         or the current timestamp if the date string is invalid.
 *
 * Example:
 * ```
 * val timestamp = parseDate("25/12/2023") // Returns the timestamp for December 25th, 2023
 * val currentTimestamp = parseDate("invalid-date") // Returns the current timestamp
 * ```
 *
 * @throws NumberFormatException if any of the day, month, or year components cannot be parsed as integers. In reality, this will not happen because `toIntOrNull` will return null in such cases, causing default values to be used.
 * @throws IllegalArgumentException If a date component is outside the valid range (for example, an invalid month) the Calendar object will handle it by wrapping to the next/previous month/year, so no exception will be thrown.
 *
 */
fun parseDate(dateStr: String): Long {
    val parts = dateStr.split("/")
    if (parts.size == 3) {
        val day = parts[0].toIntOrNull() ?: 1
        val month = parts[1].toIntOrNull() ?: 1
        val year = parts[2].toIntOrNull() ?: 2024
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        return calendar.timeInMillis
    }
    return System.currentTimeMillis()
}
