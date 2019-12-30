package com.tak8997.instastylegallery.di

import android.content.ContentResolver
import androidx.lifecycle.ViewModelProvider
import com.tak8997.instastylegallery.GalleryApp
import com.tak8997.instastylegallery.data.repository.GalleryDataRepository
import com.tak8997.instastylegallery.data.repository.GalleryRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule.ProvideModule::class])
internal interface AppModule {

    @Module
    class ProvideModule {

        @Provides
        fun provideContentResolver(): ContentResolver = GalleryApp.instance.applicationContext.contentResolver
    }

    @Binds
    fun bindViewModelFactory(viewModelFactory: GalleryViewModelFactory): ViewModelProvider.Factory

    @Binds
    fun bindGalleryRepository(galleryRepository: GalleryDataRepository): GalleryRepository
}
