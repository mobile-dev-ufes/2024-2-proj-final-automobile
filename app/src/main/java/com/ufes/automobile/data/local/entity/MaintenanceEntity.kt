package com.ufes.automobile.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

/**
 * Represents a maintenance record for a specific vehicle in the database.
 *
 * This entity is designed to store information about maintenance tasks performed on vehicles.
 * It includes details such as the description of the maintenance, the cost, the date it was performed,
 * and a reference to the vehicle it belongs to.
 *
 * @property id The unique identifier for this maintenance record. It is automatically generated.
 * @property vehicleId The ID of the vehicle this maintenance record is associated with.
 *                     It is a foreign key referencing the [VehicleEntity].
 * @property description A description of the maintenance task performed.
 * @property cost The cost associated with this maintenance task.
 * @property date The date when the maintenance task was performed, stored as a Unix timestamp (milliseconds).
 */
@Entity(
    tableName = "maintenance",
    foreignKeys = [ForeignKey(
        entity = VehicleEntity::class,
        parentColumns = ["id"],
        childColumns = ["vehicleId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["vehicleId"])]
)

data class MaintenanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val vehicleId: Int,
    val description: String,
    val cost: Float,
    val date: Long,
)