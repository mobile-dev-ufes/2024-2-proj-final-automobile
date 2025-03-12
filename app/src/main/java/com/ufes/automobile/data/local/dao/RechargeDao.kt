package com.ufes.automobile.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ufes.automobile.data.local.entity.RechargeEntity

/**
 * Data Access Object (DAO) for interacting with the 'recharges' table in the database.
 * This interface provides methods for inserting and querying recharge records.
 */
@Dao
interface RechargeDao {
    @Insert
    suspend fun insert(recharge: RechargeEntity)

    @Query("SELECT * FROM recharges WHERE vehicleId = :vehicleId")
    suspend fun getRechargesByVehicle(vehicleId: Int): List<RechargeEntity>
}