package com.wpfl5.chattutorial.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wpfl5.chattutorial.repository.AuthRepository
import com.wpfl5.chattutorial.repository.StoreRepository
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

    @ExperimentalCoroutinesApi
    @Provides
    @ActivityRetainedScoped
    fun provideStoreRepository(
        store: FirebaseFirestore
    ) : StoreRepository {
        return StoreRepository(store)
    }

}