package com.ufes.automobile.data.repository

import com.ufes.automobile.data.local.dao.AccidentDao
import com.ufes.automobile.data.local.entity.AccidentEntity
import com.ufes.automobile.domain.repository.AccidentRepository

/**
 * Implementation of the [AccidentRepository] interface.
 * This class handles the data access logic for accident entities,
 * interacting directly with the [AccidentDao] to perform database operations.
 *
 * @property dao The [AccidentDao] instance used to access the accident data in the database.
 */
class AccidentRepositoryImpl(private val dao: AccidentDao) : AccidentRepository {
    override suspend fun addAccident(accident: AccidentEntity) {
        dao.insert(accident)
    }

    override suspend fun getAccidentsByVehicle(vehicleId: Int): List<AccidentEntity> {
        return dao.getAccidentsByVehicle(vehicleId)
    }
}