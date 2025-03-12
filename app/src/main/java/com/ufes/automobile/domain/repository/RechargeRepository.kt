package com.ufes.automobile.domain.repository

import com.ufes.automobile.data.local.entity.RechargeEntity

/**
 * Interface for managing recharge data in the repository layer.
 * This interface defines the contract for interacting with the persistent storage
 * of recharge entities.
 */
interface RechargeRepository {
    suspend fun addRecharge(recharge: RechargeEntity)
    suspend fun getRechargesByVehicle(vehicleId: Int): List<RechargeEntity>
}