package com.ufes.automobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ufes.automobile.data.local.entity.AccidentEntity

/**
 * Data Access Object (DAO) for interacting with the 'accidents' table in the database.
 * This interface provides methods for inserting and retrieving accident data.
 */
@Dao
interface AccidentDao {
    @Insert
    suspend fun insert(accident: AccidentEntity)

    @Query("SELECT * FROM accidents WHERE vehicleId = :vehicleId")
    suspend fun getAccidentsByVehicle(vehicleId: Int): List<AccidentEntity>
}