package com.ufes.automobile.data.repository

import com.ufes.automobile.data.local.dao.MaintenanceDao
import com.ufes.automobile.data.local.entity.MaintenanceEntity
import com.ufes.automobile.domain.repository.MaintenanceRepository

/**
 * Implementation of the [MaintenanceRepository] interface.
 *
 * This class provides concrete implementations for interacting with the data layer
 * to manage maintenance records using the provided [MaintenanceDao].
 *
 * @property dao The [MaintenanceDao] instance used to access the database for maintenance operations.
 */
class MaintenanceRepositoryImpl(private val dao: MaintenanceDao) : MaintenanceRepository {
    override suspend fun addMaintenance(maintenance: MaintenanceEntity) {
        dao.insert(maintenance)
    }

    override suspend fun getMaintenanceByVehicle(vehicleId: Int): List<MaintenanceEntity> {
        return dao.getMaintenanceByVehicle(vehicleId)
    }
}