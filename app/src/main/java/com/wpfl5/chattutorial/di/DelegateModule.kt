package com.wpfl5.chattutorial.di

import com.wpfl5.chattutorial.ui.EventViewModelDelegate
import com.wpfl5.chattutorial.ui.EventViewModelDelegateImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DelegateModule {

    @Binds
    abstract fun bindViewModelDelegate(
        delegate: EventViewModelDelegateImp
    ) : EventViewModelDelegate

}