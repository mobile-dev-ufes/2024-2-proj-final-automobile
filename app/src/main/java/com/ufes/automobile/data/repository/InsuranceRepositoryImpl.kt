package com.ufes.automobile.data.repository

import com.ufes.automobile.data.local.dao.InsuranceDao
import com.ufes.automobile.data.local.entity.InsuranceEntity
import com.ufes.automobile.domain.repository.InsuranceRepository

/**
 * Implementation of the [InsuranceRepository] interface.
 *
 * This class provides the concrete implementation for interacting with the insurance data
 * using the [InsuranceDao]. It handles operations such as adding new insurance records
 * and retrieving insurance records associated with a specific vehicle.
 *
 * @property insuranceDao The [InsuranceDao] instance used for database interactions.
 */
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