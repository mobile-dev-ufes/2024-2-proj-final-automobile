package com.ufes.automobile.domain.model

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
