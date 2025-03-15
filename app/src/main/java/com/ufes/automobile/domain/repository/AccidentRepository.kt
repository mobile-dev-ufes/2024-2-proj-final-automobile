package com.ufes.automobile.domain.repository

import com.ufes.automobile.data.local.entity.AccidentEntity

interface AccidentRepository {
    suspend fun addAccident(accident: AccidentEntity)
    suspend fun getAccidentsByVehicle(vehicleId: Int): List<AccidentEntity>
}