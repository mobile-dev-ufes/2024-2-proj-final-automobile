package com.ufes.automobile.domain.repository

import com.ufes.automobile.data.local.entity.InsuranceEntity

interface InsuranceRepository {
    suspend fun addInsurance(insurance: InsuranceEntity)
    suspend fun getInsuranceByVehicle(vehicleId: Int): List<InsuranceEntity>
}