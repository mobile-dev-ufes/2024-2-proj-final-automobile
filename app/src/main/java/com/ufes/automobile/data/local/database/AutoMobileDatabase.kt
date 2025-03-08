package com.ufes.automobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ufes.automobile.data.local.dao.VehicleDao
import com.ufes.automobile.data.local.entity.VehicleEntity

@Database(entities = [VehicleEntity::class], version = 1, exportSchema = false)
abstract class AutoMobileDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
}