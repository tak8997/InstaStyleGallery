package com.tak8997.instastylegallery.di.uimodule

import androidx.lifecycle.ViewModel
import com.tak8997.instastylegallery.di.ViewModelKey
import com.tak8997.instastylegallery.ui.gallerydetail.GalleryDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface GalleryDetailModule {

    @Binds
    @IntoMap
    @ViewModelKey(GalleryDetailViewModel::class)
    fun bindViewModel(galleryDetailViewModel: GalleryDetailViewModel): ViewModel
}
