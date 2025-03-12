package com.ufes.automobile.data.repository

import com.ufes.automobile.data.local.dao.RechargeDao
import com.ufes.automobile.data.local.entity.RechargeEntity
import com.ufes.automobile.domain.repository.RechargeRepository

/**
 * Implementation of the [RechargeRepository] interface.
 *
 * This class provides the concrete implementation for interacting with the data layer
 * to perform operations related to recharge entities, such as adding and retrieving them.
 *
 * @property dao The [RechargeDao] instance used for database interactions.
 */
class RechargeRepositoryImpl(private val dao: RechargeDao) : RechargeRepository {
    override suspend fun addRecharge(recharge: RechargeEntity) {
        dao.insert(recharge)
    }

    override suspend fun getRechargesByVehicle(vehicleId: Int): List<RechargeEntity> {
        return dao.getRechargesByVehicle(vehicleId)
    }
}