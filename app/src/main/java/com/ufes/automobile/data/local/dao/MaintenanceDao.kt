package com.ufes.automobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ufes.automobile.data.local.entity.MaintenanceEntity

/**
 * Data Access Object (DAO) interface for interacting with the 'maintenance' table in the database.
 * This interface provides methods for inserting and querying maintenance records.
 */
@Dao
interface MaintenanceDao {
    @Insert
    suspend fun insert(maintenance: MaintenanceEntity)

    @Query("SELECT * FROM maintenance WHERE vehicleId = :vehicleId")
    suspend fun getMaintenanceByVehicle(vehicleId: Int) : List<MaintenanceEntity>
}