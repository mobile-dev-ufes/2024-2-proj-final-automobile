package com.ufes.automobile.domain.repository

import com.ufes.automobile.data.local.entity.InsuranceEntity

/**
 * Interface for managing insurance data.
 * This repository provides methods to interact with the underlying data source
 * for insurance entities, such as adding new insurance policies and retrieving
 * insurance information related to specific vehicles.
 */
interface InsuranceRepository {
    suspend fun addInsurance(insurance: InsuranceEntity)
    suspend fun getInsuranceByVehicle(vehicleId: Int): List<InsuranceEntity>
}