package com.ufes.automobile.data.repository

import com.ufes.automobile.data.local.dao.VehicleDao
import com.ufes.automobile.data.local.entity.toEntity
import com.ufes.automobile.data.local.entity.toDomainModel
import com.ufes.automobile.domain.model.Vehicle
import com.ufes.automobile.domain.repository.GarageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
}