package com.ufes.automobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ufes.automobile.data.local.entity.DisplacementEntity

/**
 * Data Access Object (DAO) interface for interacting with the 'displacements' table in the database.
 * This interface provides methods for inserting and retrieving displacement records.
 */
@Dao
interface DisplacementDao {
    @Insert
    suspend fun insert(displacement: DisplacementEntity)

    @Query("SELECT * FROM displacements WHERE vehicleId = :vehicleId")
    suspend fun getDisplacementsByVehicle(vehicleId: Int): List<DisplacementEntity>
}
