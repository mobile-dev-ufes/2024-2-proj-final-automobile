package com.ufes.automobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ufes.automobile.data.local.entity.MaintenanceReminderEntity

@Dao
interface MaintenanceReminderDao {
    @Insert
    suspend fun insert(maintenanceReminder: MaintenanceReminderEntity)

    @Query("SELECT * FROM maintenanceReminder WHERE vehicleId = :vehicleId")
    suspend fun getMaintenanceReminderByVehicle(vehicleId: Int): List<MaintenanceReminderEntity>
}