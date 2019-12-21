package com.tak8997.instastylegallery.di

import com.tak8997.instastylegallery.ui.favorite.FavoriteFragment
import com.tak8997.instastylegallery.ui.gallery.GalleryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class FragmentBindingModule {

    @ContributesAndroidInjector(modules = [GalleryModule::class])
    abstract fun galleryFragment(): GalleryFragment

    @ContributesAndroidInjector(modules = [FavoriteModule::class])
    abstract fun favoriteFragment(): FavoriteFragment
}
