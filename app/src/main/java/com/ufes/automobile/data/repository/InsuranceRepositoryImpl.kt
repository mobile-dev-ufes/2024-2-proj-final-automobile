package com.ufes.automobile.data.repository

import com.ufes.automobile.data.local.dao.InsuranceDao
import com.ufes.automobile.data.local.entity.InsuranceEntity
import com.ufes.automobile.domain.repository.InsuranceRepository

class InsuranceRepositoryImpl(
    private val insuranceDao: InsuranceDao
) : InsuranceRepository {

    override suspend fun addInsurance(insurance: InsuranceEntity) {
        insuranceDao.insert(insurance)
    }

    override suspend fun getInsuranceByVehicle(vehicleId: Int): List<InsuranceEntity> {
        return insuranceDao.getInsuranceByVehicle(vehicleId)
    }

}