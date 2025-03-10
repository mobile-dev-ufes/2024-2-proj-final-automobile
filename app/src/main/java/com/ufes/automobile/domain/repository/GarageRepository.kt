package com.ufes.automobile.domain.repository

import com.ufes.automobile.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

interface GarageRepository {
    suspend fun getVehicles(): Flow<List<Vehicle>>
    suspend fun addVehicle(vehicle: Vehicle)
    suspend fun deleteVehicle(vehicleId: Int)
    suspend fun getVehicleById(vehicleId: Int): Vehicle?
}