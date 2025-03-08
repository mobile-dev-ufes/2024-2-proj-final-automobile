package com.ufes.automobile.di

import com.ufes.automobile.data.local.dao.VehicleDao
import com.ufes.automobile.data.repository.GarageRepositoryImpl
import com.ufes.automobile.domain.repository.GarageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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