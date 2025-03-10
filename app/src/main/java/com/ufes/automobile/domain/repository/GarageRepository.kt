package com.ufes.automobile.domain.repository

import com.ufes.automobile.domain.model.Vehicle
import kotlinx.coroutines.flow.Flow

/**
 * Interface for interacting with a data source for garage vehicles.
 * This interface defines methods for retrieving, adding, deleting, and fetching vehicles by ID.
 */
interface GarageRepository {
    suspend fun getVehicles(): Flow<List<Vehicle>>
    suspend fun addVehicle(vehicle: Vehicle)
    suspend fun deleteVehicle(vehicleId: Int)
    suspend fun getVehicleById(vehicleId: Int): Vehicle?
}