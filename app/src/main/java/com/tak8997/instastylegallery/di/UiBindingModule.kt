package com.tak8997.instastylegallery.di

import com.tak8997.instastylegallery.di.uimodule.FavoriteModule
import com.tak8997.instastylegallery.di.uimodule.GalleryDetailModule
import com.tak8997.instastylegallery.di.uimodule.GalleryModule
import com.tak8997.instastylegallery.di.uimodule.MainModule
import com.tak8997.instastylegallery.ui.MainActivity
import com.tak8997.instastylegallery.ui.favorite.FavoriteFragment
import com.tak8997.instastylegallery.ui.gallery.GalleryFragment
import com.tak8997.instastylegallery.ui.gallerydetail.GalleryDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [UiBindingModule.FragmentModule::class])
internal interface UiBindingModule {

    @ContributesAndroidInjector(modules = [MainModule::class])
    fun mainActivity(): MainActivity

    @Module
    abstract class FragmentModule {

        @ContributesAndroidInjector(modules = [GalleryModule::class])
        abstract fun galleryFragment(): GalleryFragment

        @ContributesAndroidInjector(modules = [FavoriteModule::class])
        abstract fun favoriteFragment(): FavoriteFragment

        @ContributesAndroidInjector(modules = [GalleryDetailModule::class])
        abstract fun galleryDetailFragment(): GalleryDetailFragment
    }
}
