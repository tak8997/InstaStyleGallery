package com.tak8997.instastylegallery.di.uimodule

import androidx.lifecycle.ViewModel
import com.tak8997.instastylegallery.di.ViewModelKey
import com.tak8997.instastylegallery.ui.gallery.GalleryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface GalleryModule {

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel::class)
    fun bindViewModel(viewmodel: GalleryViewModel): ViewModel
}
