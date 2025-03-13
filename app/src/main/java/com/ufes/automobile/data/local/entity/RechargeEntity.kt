package com.ufes.automobile.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

/**
 * Represents a recharge record in the local database.
 *
 * This entity stores information about a single recharge event, including the vehicle it's associated with,
 * the type of recharge (electric or not), the amount recharged, the cost, and the date of the recharge.
 *
 * @property id The unique identifier for the recharge record. This is auto-generated by the database.
 * @property vehicleId The ID of the vehicle that this recharge is associated with.
 *                    This is a foreign key referencing the `VehicleEntity` table.
 * @property isElectric Indicates whether the recharge was electric (true) or not (false).
 * @property amount The amount recharged (e.g., liters of fuel, kWh of electricity).
 * @property cost The cost associated with this recharge.
 * @property date The timestamp (in milliseconds) representing the date and time of the recharge.
 *
 * @constructor Creates a new RechargeEntity instance.
 */
@Entity(
    tableName = "recharges",
    foreignKeys = [ForeignKey(
        entity = VehicleEntity::class,
        parentColumns = ["id"],
        childColumns = ["vehicleId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["vehicleId"])]
)
data class RechargeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val vehicleId: Int,
    val isElectric: Boolean,
    val amount: Float,
    val cost: Float,
    val date: Long
)