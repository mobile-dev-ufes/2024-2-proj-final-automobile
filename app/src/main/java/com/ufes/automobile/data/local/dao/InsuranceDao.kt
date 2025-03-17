package com.ufes.automobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ufes.automobile.data.local.entity.InsuranceEntity

/**
 * Data Access Object (DAO) interface for interacting with the 'insurance' table in the database.
 * This interface provides methods for inserting and retrieving insurance records.
 */
@Dao
interface InsuranceDao {
    @Insert
    suspend fun insert(insurance: InsuranceEntity)

    @Query("SELECT * FROM insurance WHERE vehicleId = :vehicleId")
    suspend fun getInsuranceByVehicle(vehicleId: Int) : List<InsuranceEntity>
}