package com.ufes.automobile.di

import com.ufes.automobile.data.local.dao.DisplacementDao
import com.ufes.automobile.data.local.dao.MaintenanceDao
import com.ufes.automobile.data.local.dao.RechargeDao
import com.ufes.automobile.data.local.dao.VehicleDao
import com.ufes.automobile.data.repository.DisplacementRepositoryImpl
import com.ufes.automobile.data.repository.GarageRepositoryImpl
import com.ufes.automobile.data.repository.MaintenanceRepositoryImpl
import com.ufes.automobile.data.repository.RechargeRepositoryImpl
import com.ufes.automobile.domain.repository.DisplacementRepository
import com.ufes.automobile.domain.repository.GarageRepository
import com.ufes.automobile.domain.repository.MaintenanceRepository
import com.ufes.automobile.domain.repository.RechargeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * **RepositoryModule**
 *
 * This Dagger Hilt module is responsible for providing singleton instances of repository classes,
 * which serve as the primary interface for interacting with data sources. It is installed in the
 * [SingletonComponent], ensuring that the provided repository instances are application-scoped
 * and created only once.
 *
 * This module centralizes the creation and management of repositories, promoting a clean separation
 * of concerns between data access logic and other parts of the application.
 *
 * **Key Features:**
 * - **Dagger Hilt Module:** Uses `@Module` to define this class as a Dagger module, enabling dependency
 *   injection.
 * - **Singleton Scope:** Uses `@InstallIn(SingletonComponent::class)` to bind the module to the
 *   application's lifecycle, ensuring singleton scope for all provided instances.
 * - **Provider Methods:** Employs `@Provides` to define methods that construct and provide
 *   instances of various repository implementations.
 * - **Singleton Instances:** Uses `@Singleton` to guarantee that each repository instance is created
 *   only once and reused throughout the application.
 * - **Clear Dependencies:** Clearly outlines the dependencies required by each repository,
 *   facilitating understanding and maintainability.
 *
 * **Provided Repositories:**
 * - [GarageRepository]: Provides an instance of [GarageRepositoryImpl], responsible for managing vehicle data.
 *   - **Dependencies:** [VehicleDao] for database interactions.
 * - [DisplacementRepository]: Provides an instance of [DisplacementRepositoryImpl], responsible for managing vehicle displacement data.
 *   - **Dependencies:** [DisplacementDao] for database interactions.
 * - [MaintenanceRepository]: Provides an instance of [MaintenanceRepositoryImpl], responsible for managing maintenance data.
 *   - **Dependencies:** [MaintenanceDao] for database interactions.
 * - [RechargeRepository]: Provides an instance of [RechargeRepositoryImpl], responsible for managing recharge data.
 *   - **Dependencies:** [RechargeDao] for database interactions.
 *
 * **Purpose:**
 * The repositories provided by this module act as intermediaries between the application's
 * business logic (e.g., ViewModels, Use Cases) and the underlying data sources (e.g., Room DAOs,
 * network APIs). This design promotes:
 * - **Abstraction:** Hides the details of data access from the rest of the */
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

    @Provides
    @Singleton
    fun provideDisplacementRepository(
        displacementDao: DisplacementDao,
    ): DisplacementRepository {
        return DisplacementRepositoryImpl(displacementDao)
    }

    @Provides
    @Singleton
    fun provideMaintenanceRepository(
        maintenanceDao: MaintenanceDao,
    ): MaintenanceRepository {
        return MaintenanceRepositoryImpl(maintenanceDao)
    }

    @Provides
    @Singleton
    fun provideRechargeRepository(
        rechargeDao: RechargeDao,
    ): RechargeRepository {
        return RechargeRepositoryImpl(rechargeDao)
    }
}