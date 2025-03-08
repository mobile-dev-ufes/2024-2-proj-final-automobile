package com.ufes.automobile.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ufes.automobile.domain.model.Vehicle

@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val brand: String,
    val model: String,
    val manufacturingYear: Int,
    val purchaseDate: Long,
    val isElectric: Boolean,
    val batteryCapacity: Float?,
    val autonomy: Float?,
    val tankCapacity: Float?
)

fun VehicleEntity.toDomainModel(): Vehicle {
    return Vehicle(
        id = id,
        brand = brand,
        model = model,
        manufacturingYear = manufacturingYear,
        purchaseDate = purchaseDate,
        isElectric = isElectric,
        batteryCapacity = batteryCapacity,
        autonomy = autonomy,
        tankCapacity = tankCapacity
    )
}

fun Vehicle.toEntity(): VehicleEntity {
    return VehicleEntity(
        id = id,
        brand = brand,
        model = model,
        manufacturingYear = manufacturingYear,
        purchaseDate = purchaseDate,
        isElectric = isElectric,
        batteryCapacity = batteryCapacity,
        autonomy = autonomy,
        tankCapacity = tankCapacity
    )
}