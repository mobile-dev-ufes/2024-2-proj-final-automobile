package com.ufes.automobile.domain.repository

import com.ufes.automobile.data.local.entity.DisplacementEntity

/**
 * Interface for managing displacement data within the application.
 * This repository provides methods for adding and retrieving displacement records.
 */
interface DisplacementRepository {
    suspend fun addDisplacement(displacement: DisplacementEntity)
    suspend fun getDisplacementsByVehicle(vehicleId: Int): List<DisplacementEntity>
}