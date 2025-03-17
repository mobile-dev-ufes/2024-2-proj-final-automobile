package com.ufes.automobile.data.repository

import com.ufes.automobile.data.local.dao.MaintenanceReminderDao
import com.ufes.automobile.data.local.entity.MaintenanceReminderEntity
import com.ufes.automobile.domain.repository.MaintenanceReminderRepository

/**
 * Implementation of the [MaintenanceReminderRepository] interface.
 *
 * This class provides concrete implementations for interacting with the data layer
 * to manage maintenance reminder entities. It uses a [MaintenanceReminderDao] to
 * perform database operations.
 *
 * @property dao The [MaintenanceReminderDao] instance used for database access.
 */
class MaintenanceReminderRepositoryImpl(private val dao: MaintenanceReminderDao) :
    MaintenanceReminderRepository {
    override suspend fun addMaintenanceReminder(maintenanceReminder: MaintenanceReminderEntity) {
        dao.insert(maintenanceReminder)
    }

    override suspend fun getMaintenanceReminderByVehicle(vehicleId: Int): List<MaintenanceReminderEntity> {
        return dao.getMaintenanceReminderByVehicle(vehicleId)
    }

}