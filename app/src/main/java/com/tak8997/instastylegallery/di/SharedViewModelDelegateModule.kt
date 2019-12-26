package com.tak8997.instastylegallery.di

import com.tak8997.instastylegallery.ui.SharedViewModelDelegate
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface SharedViewModelDelegateModule {

    @Singleton
    @Binds
    fun provideSharedViewModelDelegate(sharedViewModelDelegate: SharedViewModelDelegate.ViewModel): SharedViewModelDelegate
}
