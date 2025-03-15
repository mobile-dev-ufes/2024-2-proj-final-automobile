package com.ufes.automobile.domain.repository

import com.ufes.automobile.data.local.entity.MaintenanceReminderEntity

interface MaintenanceReminderRepository {
    suspend fun addMaintenanceReminder(maintenanceReminder: MaintenanceReminderEntity)
    suspend fun getMaintenanceReminderByVehicle(vehicleId: Int): List<MaintenanceReminderEntity>
}