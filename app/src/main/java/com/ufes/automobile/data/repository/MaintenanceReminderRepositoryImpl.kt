package com.ufes.automobile.data.repository

import com.ufes.automobile.data.local.dao.MaintenanceReminderDao
import com.ufes.automobile.data.local.entity.MaintenanceReminderEntity
import com.ufes.automobile.domain.repository.MaintenanceReminderRepository

class MaintenanceReminderRepositoryImpl(private val dao: MaintenanceReminderDao) :
    MaintenanceReminderRepository {
    override suspend fun addMaintenanceReminder(maintenanceReminder: MaintenanceReminderEntity) {
        dao.insert(maintenanceReminder)
    }

    override suspend fun getMaintenanceReminderByVehicle(vehicleId: Int): List<MaintenanceReminderEntity> {
        return dao.getMaintenanceReminderByVehicle(vehicleId)
    }

}