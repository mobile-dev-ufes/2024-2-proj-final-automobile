package com.ufes.automobile.domain.repository

import com.ufes.automobile.data.local.entity.AccidentEntity

/**
 * Interface for managing accident data.
 * This repository provides methods to interact with the underlying data source
 * for accident-related operations.
 */
interface AccidentRepository {
    suspend fun addAccident(accident: AccidentEntity)
    suspend fun getAccidentsByVehicle(vehicleId: Int): List<AccidentEntity>
}