package com.ufes.automobile.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents a maintenance reminder for a specific vehicle.
 *
 * This entity stores information about a scheduled maintenance task,
 * including the vehicle it's associated with, the due date, and a description
 * of the maintenance to be performed.
 *
 * @property id The unique identifier for the maintenance reminder. This is
 *   automatically generated by the database.
 * @property vehicleId The ID of the [VehicleEntity] that this reminder is
 *   associated with. This is a foreign key referencing the 'id' column of
 *   the 'vehicle' table.
 * @property reminderDate The timestamp (in milliseconds since the epoch)
 *   representing the date when the maintenance is due.
 * @property description A textual description of the maintenance task.
 *
 * @constructor Creates a MaintenanceReminderEntity object.
 *
 * @sample
 * // Example of creating a MaintenanceReminderEntity
 * val reminder = MaintenanceReminderEntity(
 *     vehicleId = 1,
 *     reminderDate = System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000, // 7 days from now
 *     description = "Oil change and tire rotation"
 * )
 *
 * @see VehicleEntity
 */
@Entity(
    tableName = "maintenanceReminder",
    foreignKeys = [ForeignKey(
        entity = VehicleEntity::class,
        parentColumns = ["id"],
        childColumns = ["vehicleId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["vehicleId"])]
)

data class MaintenanceReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val vehicleId: Int,
    val reminderDate: Long,
    val description: String,
)