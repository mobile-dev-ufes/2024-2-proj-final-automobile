package com.ufes.automobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ufes.automobile.data.local.entity.MaintenanceReminderEntity

/**
 * Data Access Object (DAO) for interacting with the [MaintenanceReminderEntity] table.
 *
 * This interface provides methods for performing database operations related to maintenance reminders,
 * such as inserting new reminders and retrieving reminders associated with a specific vehicle.
 */
@Dao
interface MaintenanceReminderDao {
    @Insert
    suspend fun insert(maintenanceReminder: MaintenanceReminderEntity)

    @Query("SELECT * FROM maintenanceReminder WHERE vehicleId = :vehicleId")
    suspend fun getMaintenanceReminderByVehicle(vehicleId: Int): List<MaintenanceReminderEntity>
}