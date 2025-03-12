package com.ufes.automobile.di

import android.content.Context
import androidx.room.Room
import com.ufes.automobile.data.local.dao.DisplacementDao
import com.ufes.automobile.data.local.dao.MaintenanceDao
import com.ufes.automobile.data.local.dao.RechargeDao
import com.ufes.automobile.data.local.dao.VehicleDao
import com.ufes.automobile.data.local.database.AutoMobileDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * **DatabaseModule**
 *
 * This Dagger Hilt module provides dependencies related to the Room database.
 * It configures and provides the `AutoMobileDatabase` instance, as well as
 * Data Access Objects (DAOs) for various entities within the database.
 *
 * **Key Features:**
 *   - **Database Instance:** Provides a singleton instance of `AutoMobileDatabase`
 *     using the Room library.
 *   - **DAO Provision:** Provides singleton instances of the following DAOs:
 *     - `VehicleDao`: For interacting with vehicle data.
 *     - `DisplacementDao`: For interacting with displacement data.
 *     - `MaintenanceDao`: For interacting with maintenance data.
 *     - `RechargeDao`: For interacting with recharge data.
 *   - **Database Configuration:**
 *     - Sets the database name to "automobile_db".
 *     - Configures the database to perform a destructive migration if the schema
 *       is updated and a non-destructive migration cannot be performed.
 *   - **Hilt Integration:** Utilizes Dagger Hilt for dependency injection.
 *   - **Singleton Scope:** All provided dependencies are in the singleton scope,
 *     ensuring that only one instance of each is created throughout the application's
 *     lifecycle.
 *   - **Application Context:** It is configured to use the Application Context to avoid Memory Leaks.
 *
 * **Usage:**
 *  Hilt will automatically inject these dependencies into any class that requires them,
 *  provided those classes are also annotated for Hilt injection.
 *
 * **Example Inject:**
 * ```
 * @HiltViewModel
 * class MyViewModel @Inject constructor(
 *      private val vehicleDao: VehicleDao
 * ) : ViewModel() {
 *   // ... use vehicleDao to do operations in DB
 * }
 * ```
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AutoMobileDatabase {
        return Room.databaseBuilder(
            context,
            AutoMobileDatabase::class.java,
            "automobile_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideVehicleDao(database: AutoMobileDatabase): VehicleDao {
        return database.vehicleDao()
    }

    @Provides
    @Singleton
    fun provideDisplacementDao(database: AutoMobileDatabase): DisplacementDao {
        return database.displacementDao()
    }

    @Provides
    @Singleton
    fun provideMaintenanceDao(database: AutoMobileDatabase): MaintenanceDao {
        return database.maintenanceDao()
    }

    @Provides
    @Singleton
    fun provideRechargeDao(database: AutoMobileDatabase): RechargeDao {
        return database.rechargeDao()
    }
}