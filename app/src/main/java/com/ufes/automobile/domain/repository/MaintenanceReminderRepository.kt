package com.ufes.automobile.domain.repository

import com.ufes.automobile.data.local.entity.MaintenanceReminderEntity

/**
 * Interface for managing maintenance reminders in a data source.
 * This repository provides methods for adding and retrieving maintenance reminders
 * associated with specific vehicles.
 */
interface MaintenanceReminderRepository {
    suspend fun addMaintenanceReminder(maintenanceReminder: MaintenanceReminderEntity)
    suspend fun getMaintenanceReminderByVehicle(vehicleId: Int): List<MaintenanceReminderEntity>
}