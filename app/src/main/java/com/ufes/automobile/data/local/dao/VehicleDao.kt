package com.ufes.automobile.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ufes.automobile.data.local.entity.VehicleEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) interface for interacting with the 'vehicles' table in the database.
 * This interface provides methods for performing CRUD (Create, Read, Update, Delete) operations
 * on VehicleEntity objects.
 */
@Dao
interface VehicleDao {
    @Insert
    suspend fun insert(vehicle: VehicleEntity)

    @Update
    suspend fun update(vehicle: VehicleEntity)

    @Delete
    suspend fun delete(vehicle: VehicleEntity)

    @Query("SELECT * FROM vehicles")
    fun getAllVehicles(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM vehicles WHERE id = :vehicleId")
    suspend fun getVehicleById(vehicleId: Int): VehicleEntity?

    @Query("DELETE FROM vehicles WHERE id = :vehicleId")
    suspend fun deleteById(vehicleId: Int)
}