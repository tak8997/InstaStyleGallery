package com.tak8997.instastylegallery.di

import androidx.lifecycle.ViewModelProvider
import com.tak8997.instastylegallery.data.repository.GalleryDataRepository
import com.tak8997.instastylegallery.data.repository.GalleryRepository
import dagger.Binds
import dagger.Module

@Module
internal interface AppModule {

    @Binds
    fun bindViewModelFactory(viewModelFactory: GalleryViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun bindGalleryRepository(galleryRepository: GalleryDataRepository): GalleryRepository
}
