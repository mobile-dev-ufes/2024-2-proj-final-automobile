package com.ufes.automobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ufes.automobile.data.local.dao.DisplacementDao
import com.ufes.automobile.data.local.dao.MaintenanceDao
import com.ufes.automobile.data.local.dao.RechargeDao
import com.ufes.automobile.data.local.dao.VehicleDao
import com.ufes.automobile.data.local.entity.DisplacementEntity
import com.ufes.automobile.data.local.entity.VehicleEntity
import com.ufes.automobile.data.local.entity.MaintenanceEntity
import com.ufes.automobile.data.local.entity.RechargeEntity

/**
 * `AutoMobileDatabase` is the Room database for the application, responsible for managing the
 * persistent storage of vehicle-related data.
 *
 * This class serves as the central access point for interacting with the underlying SQLite database.
 * It defines the database schema, version, and provides Data Access Objects (DAOs) for interacting
 * with each entity's data. The database is versioned to support schema migrations.
 *
 * **Entities:**
 *   - [VehicleEntity]: Represents a single vehicle record.
 *   - [DisplacementEntity]: Represents a displacement record (e.g., fuel usage, mileage).
 *   - [MaintenanceEntity]: Represents a maintenance record (e.g., oil change, tire rotation).
 *   - [RechargeEntity]: Represents a recharge record (e.g., electric vehicle charging).
 *
 * **Database Version:**
 *   - 1: Initial database version (included only Vehicle entity).
 *   - 2: Added Displacement entity.
 *   - 3: Added Maintenance and Recharge entities.
 *
 * **Export Schema:**
 *   - true: The database schema is exported to a JSON file. This allows tracking changes to the database structure over time.
 *     This is useful for debugging and schema management, especially in collaborative projects.
 *
 * **Data Access Objects (DAOs):**
 *   - [VehicleDao]: Provides methods for interacting with the [VehicleEntity] table (e.g., inserting, querying, updating, deleting).
 *   - [DisplacementDao]: Provides methods for interacting with the [DisplacementEntity] table.
 *   - [MaintenanceDao]: Provides methods for interacting with the [MaintenanceEntity] table.
 *   - [RechargeDao]: Provides methods for interacting with the [RechargeEntity] table.
 *
 * **Usage:**
 *   - This class should be instantiated as a singleton to ensure a single database instance
 *     throughout the application lifecycle. This is commonly achieved using a dependency
 *     injection framework like Hilt or Koin.
 *   - Access to the DAOs is provided via their respective accessor methods (e.g., [vehicleDao], [displacementDao]).
 *
 * **Example:**
 *
 * ```kotlin
 *  // Get the AutoMobileDatabase instance (typically using a dependency injection framework)
 *  val database: AutoMobileDatabase */
@Database(entities = [
        VehicleEntity::class,
        DisplacementEntity::class,
        MaintenanceEntity::class,
        RechargeEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AutoMobileDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
    abstract fun displacementDao(): DisplacementDao
    abstract fun maintenanceDao(): MaintenanceDao
    abstract fun rechargeDao(): RechargeDao
}