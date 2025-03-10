package com.ufes.automobile.di

import android.content.Context
import androidx.room.Room
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
 * This Hilt module provides dependencies related to the Room database.
 * It is installed in the [SingletonComponent], making these dependencies available throughout the application.
 *
 * **Key Responsibilities:**
 *  - Provides a singleton instance of the [AutoMobileDatabase].
 *  - Provides a singleton instance of the [VehicleDao].
 *
 * **Dependencies:**
 * - Application Context ([Context])
 *
 * **Provided Dependencies:**
 *  - [AutoMobileDatabase] - The Room database instance.
 *  - [VehicleDao] - The Data Access Object for interacting with the Vehicle entity.
 *
 * **Usage:**
 *  This module is automatically used by Hilt. Inject [AutoMobileDatabase] or [VehicleDao] in your
 *  classes using the @Inject annotation. For example:
 *
 *  ```kotlin
 *  class MyRepository @Inject constructor(
 *      private val vehicleDao: VehicleDao
 *  ) {
 *      // ...
 *  }
 *  ```
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
        ).build()
    }

    @Provides
    @Singleton
    fun provideVehicleDao(database: AutoMobileDatabase): VehicleDao {
        return database.vehicleDao()
    }
}