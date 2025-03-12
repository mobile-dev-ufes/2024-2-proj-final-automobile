package com.ufes.automobile.data.repository

import com.ufes.automobile.data.local.dao.DisplacementDao
import com.ufes.automobile.data.local.entity.DisplacementEntity
import com.ufes.automobile.domain.repository.DisplacementRepository

/**
 * Implementation of the [DisplacementRepository] interface.
 *
 * This class provides concrete implementations for interacting with displacement data
 * stored in a persistent data source via the [DisplacementDao].
 *
 * @property dao The [DisplacementDao] instance used for data access.
 */
class DisplacementRepositoryImpl(private val dao: DisplacementDao) : DisplacementRepository {
    override suspend fun addDisplacement(displacement: DisplacementEntity) {
        dao.insert(displacement)
    }

    override suspend fun getDisplacementsByVehicle(vehicleId: Int): List<DisplacementEntity> {
        return dao.getDisplacementsByVehicle(vehicleId)
    }
}