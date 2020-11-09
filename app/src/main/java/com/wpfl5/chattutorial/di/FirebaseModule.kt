package com.wpfl5.chattutorial.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth() : FirebaseAuth{
        return Firebase.auth
    }

    @Singleton
    @Provides
    fun provideFireStore() : FirebaseFirestore{
        return Firebase.firestore
    }

    @Singleton
    @Provides
    fun provideFirebaseStorage() : FirebaseStorage{
        return Firebase.storage
    }

    @Singleton
    @Provides
    fun provideFirebaseId() : FirebaseInstanceId{
        return FirebaseInstanceId.getInstance()
    }

}