package com.ufes.automobile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * NetworkModule
 *
 * This Hilt module provides network-related dependencies for the application.
 * It is installed in the SingletonComponent, making the provided dependencies
 * available throughout the application's lifecycle.
 *
 * Currently, it provides a Retrofit instance configured with a base URL and
 * Gson converter.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /*
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): AutoMobileApi {
        return retrofit.create(AutoMobileApi::class.java)
    }
    */
}


