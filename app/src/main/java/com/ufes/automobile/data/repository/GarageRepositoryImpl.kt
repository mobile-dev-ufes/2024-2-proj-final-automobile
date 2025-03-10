package com.ufes.automobile.data.repository

import com.ufes.automobile.data.local.dao.VehicleDao
import com.ufes.automobile.data.local.entity.toEntity
import com.ufes.automobile.data.local.entity.toDomainModel
import com.ufes.automobile.domain.model.Vehicle
import com.ufes.automobile.domain.repository.GarageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of the [GarageRepository] interface.
 *
 * This class interacts with the [VehicleDao] to perform database operations
 * related to vehicles. It provides methods for retrieving, adding, deleting,
 * and getting vehicles by their ID.
 *
 * @property vehicleDao The Data Access Object (DAO) for vehicle entities.
 */
class GarageRepositoryImpl(private val vehicleDao: VehicleDao) : GarageRepository {
    override suspend fun getVehicles(): Flow<List<Vehicle>> {
        return vehicleDao.getAllVehicles().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun addVehicle(vehicle: Vehicle) {
        vehicleDao.insert(vehicle.toEntity())
    }

    override suspend fun deleteVehicle(vehicleId: Int) {
        vehicleDao.deleteById(vehicleId)
    }

    override suspend fun getVehicleById(vehicleId: Int): Vehicle? {
        return vehicleDao.getVehicleById(vehicleId)?.toDomainModel()
    }
}