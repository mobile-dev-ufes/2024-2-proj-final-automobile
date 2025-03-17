package com.ufes.automobile.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents an accident record in the database.
 *
 * This entity stores information about a specific accident, including the associated vehicle,
 * the date of the accident, a description, and the location.
 *
 * @property id The unique identifier for the accident. This is automatically generated.
 * @property vehicleId The ID of the vehicle involved in the accident. This is a foreign key
 *                     referencing the `VehicleEntity`.
 * @property date The timestamp representing the date and time of the accident (stored as a Long).
 * @property description A textual description of the accident.
 * @property location The location where the accident occurred.
 *
 * @constructor Creates an instance of AccidentEntity.
 *
 * @Entity This annotation marks this class as a Room database entity.
 * @property tableName The name of the database table for storing accident records ("accidents").
 * @property foreignKeys Defines the foreign key constraint.
 *  - entity: `VehicleEntity::class` - Specifies the related entity.
 *  - parentColumns: `["id"]` - The column in `VehicleEntity` that is referenced.
 *  - childColumns: `["vehicleId"]` - The column in `AccidentEntity` that holds the foreign key.
 *  - onDelete: `ForeignKey.CASCADE` - Specifies that when a `VehicleEntity` is deleted,
 *                                     all associated `AccidentEntity` records should also be deleted.
 * @property indices Defines an index on the `vehicleId` column to optimize queries that filter by vehicle.
 *      - Index(value = ["vehicleId"]): Creates an index on the "vehicleId" column.
 *
 */
@Entity(
    tableName = "accidents",
    foreignKeys = [ForeignKey(
            entity = VehicleEntity::class,
            parentColumns = ["id"],
            childColumns = ["vehicleId"],
            onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["vehicleId"])]
)
data class AccidentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val vehicleId: Int,
    val date: Long,
    val description: String,
    val location: String
)