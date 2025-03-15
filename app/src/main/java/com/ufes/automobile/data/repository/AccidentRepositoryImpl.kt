package com.ufes.automobile.data.repository

import com.ufes.automobile.data.local.dao.AccidentDao
import com.ufes.automobile.data.local.entity.AccidentEntity
import com.ufes.automobile.domain.repository.AccidentRepository

class AccidentRepositoryImpl(private val dao: AccidentDao) : AccidentRepository {
    override suspend fun addAccident(accident: AccidentEntity) {
        dao.insert(accident)
    }

    override suspend fun getAccidentsByVehicle(vehicleId: Int): List<AccidentEntity> {
        return dao.getAccidentsByVehicle(vehicleId)
    }
}