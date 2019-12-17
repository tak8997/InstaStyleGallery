package com.tak8997.instastylegallery.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
internal interface AppModule {

    @Binds
    fun bindsViewModelFactory(viewModelFactory: GalleryViewModelFactory): ViewModelProvider.Factory
}