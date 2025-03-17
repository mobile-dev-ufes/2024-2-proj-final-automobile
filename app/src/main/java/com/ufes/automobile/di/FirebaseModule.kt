package com.ufes.automobile.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * FirebaseModule
 *
 * This Dagger Hilt module provides dependencies related to Firebase.
 * It currently provides an instance of FirebaseAuth for authentication.
 *
 * Usage:
 * Inject FirebaseAuth where needed in your application:
 * ```
 * @Inject lateinit var firebaseAuth: FirebaseAuth
 * ```
 */
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}