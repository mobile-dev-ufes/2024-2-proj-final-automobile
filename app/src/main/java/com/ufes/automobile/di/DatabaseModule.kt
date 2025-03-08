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