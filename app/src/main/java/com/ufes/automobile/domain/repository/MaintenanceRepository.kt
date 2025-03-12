package com.ufes.automobile.domain.repository

import com.ufes.automobile.data.local.entity.MaintenanceEntity

/**
 * Interface for managing maintenance records in a data source.
 *
 * This interface defines the operations for adding and retrieving maintenance information
 * related to vehicles.
 */
interface MaintenanceRepository {
    suspend fun addMaintenance(maintenance: MaintenanceEntity)
    suspend fun getMaintenanceByVehicle(vehicleId: Int): List<MaintenanceEntity>
}