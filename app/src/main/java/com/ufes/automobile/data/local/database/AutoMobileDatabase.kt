package com.ufes.automobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ufes.automobile.data.local.dao.VehicleDao
import com.ufes.automobile.data.local.entity.VehicleEntity

/**
 * [AutoMobileDatabase] is the Room database for the application, managing the persistent storage
 * of vehicle data.
 *
 * This class provides a database holder and serves as the main access point for the underlying
 * persisted relational data. The database is versioned to allow for schema migrations.
 *
 * Entities:
 *   - [VehicleEntity]: Represents a single vehicle record in the database.
 *
 * Database Version:
 *   - 1: Initial database version.
 *
 * Export Schema:
 *   - false: The database schema is not exported to a JSON file. This is a good practice for most
 *     applications as it simplifies development and reduces file clutter unless schema history
 *     tracking is specifically needed.
 *
 * Data Access Objects (DAOs):
 *   - [VehicleDao]: Provides methods for interacting with the [VehicleEntity] table, such as
 *     inserting, querying, updating, and deleting vehicle records.
 *
 * Usage:
 *   - This class should be instantiated as a singleton to ensure a single database instance
 *     throughout the application lifecycle.
 *   - Access to the [VehicleDao] is provided via the [vehicleDao] method.
 *
 * Example:
 *
 * ```kotlin
 *  // Get the AutoMobileDatabase instance (typically using a dependency injection framework)
 *  val database: AutoMobileDatabase = // ... get the database instance
 *
 *  // Get the VehicleDao instance
 *  val vehicleDao: VehicleDao = database.vehicleDao()
 *
 *  // Perform database operations using the DAO
 *  val allVehicles: List<VehicleEntity> = vehicleDao.getAllVehicles()
 * ```
 */
@Database(entities = [VehicleEntity::class], version = 1, exportSchema = false)
abstract class AutoMobileDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
}