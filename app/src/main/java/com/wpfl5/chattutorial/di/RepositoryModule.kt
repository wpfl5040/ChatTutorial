package com.wpfl5.chattutorial.di

import com.google.firebase.auth.FirebaseAuth
import com.wpfl5.chattutorial.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {


    @ExperimentalCoroutinesApi
    @Provides
    @ActivityRetainedScoped
    fun provideAuthRepository(
        auth: FirebaseAuth
    ) : AuthRepository {
        return AuthRepository(auth)
    }


}