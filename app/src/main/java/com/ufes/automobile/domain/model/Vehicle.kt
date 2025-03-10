package com.ufes.automobile.domain.model

/**
 * Represents a vehicle with various attributes.
 *
 * @property id The unique identifier of the vehicle.
 * @property brand The brand of the vehicle (e.g., "Toyota", "Tesla").
 * @property model The specific model of the vehicle (e.g., "Camry", "Model 3").
 * @property manufacturingYear The year the vehicle was manufactured.
 * @property purchaseDate The timestamp (in milliseconds since epoch) when the vehicle was purchased.
 * @property isElectric Indicates whether the vehicle is electric (true) or not (false).
 * @property batteryCapacity The battery capacity of the vehicle in kWh (kilowatt-hours), if it's electric. Null otherwise.
 * @property autonomy The estimated range (in kilometers or miles, depending on the context) the vehicle can travel on a full charge or tank. Null if not applicable (e.g. not an electric vehicle or a combustion vehicle)
 * @property tankCapacity The fuel tank capacity of the vehicle in liters or gallons, if it's a combustion vehicle. Null otherwise.
 */
data class Vehicle(
    val id: Int,
    val brand: String,
    val model: String,
    val manufacturingYear: Int,
    val purchaseDate: Long,
    val isElectric: Boolean,
    val batteryCapacity: Float?,
    val autonomy: Float?,
    val tankCapacity: Float?
)
