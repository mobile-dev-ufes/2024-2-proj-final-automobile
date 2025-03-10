package com.ufes.automobile.di

import com.ufes.automobile.data.local.dao.VehicleDao
import com.ufes.automobile.data.repository.GarageRepositoryImpl
import com.ufes.automobile.domain.repository.GarageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * **RepositoryModule**
 *
 * This Dagger Hilt module is responsible for providing instances of repository classes
 * that are used for interacting with data sources. It is installed in the [SingletonComponent],
 * meaning the provided instances will be available throughout the application's lifecycle
 * and will be created only once (singleton scope).
 *
 * Currently, this module provides the [GarageRepository] implementation, which depends on
 * the [VehicleDao] for database interactions.
 *
 * **Key Features:**
 * - Uses `@Module` to define this class as a Dagger module.
 * - Uses `@InstallIn(SingletonComponent::class)` to indicate that the module's bindings
 *   are available throughout the application in a singleton scope.
 * - Uses `@Provides` to define methods that provide instances of the required classes.
 * - Uses `@Singleton` to ensure that the provided instances are created only once.
 *
 * **Provided Repositories:**
 * - [GarageRepository]: Provides an instance of [GarageRepositoryImpl].
 *
 * **Dependencies:**
 * - [VehicleDao]: Used by [GarageRepositoryImpl] to access and manipulate vehicle data in the database.
 *
 * **Future considerations**
 *  - More repositories like, for example, a `PartsRepository` or `UserRepository` can be added here.
 *  - When an API service is implemented, it can be added as dependency in the method `provideGarageRepository` to provide an alternative datasource.
 *
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideGarageRepository(
        vehicleDao: VehicleDao,
        /* apiService: AutoMobileApi */
    ): GarageRepository {
        return GarageRepositoryImpl(vehicleDao)
    }
}