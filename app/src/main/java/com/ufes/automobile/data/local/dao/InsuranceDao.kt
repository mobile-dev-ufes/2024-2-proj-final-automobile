package com.ufes.automobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ufes.automobile.data.local.entity.InsuranceEntity

@Dao
interface InsuranceDao {
    @Insert
    suspend fun insert(insurance: InsuranceEntity)

    @Query("SELECT * FROM insurance WHERE vehicleId = :vehicleId")
    suspend fun getInsuranceByVehicle(vehicleId: Int) : List<InsuranceEntity>
}