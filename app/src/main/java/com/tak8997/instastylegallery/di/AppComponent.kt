package com.tak8997.instastylegallery.di

import android.content.Context
import com.tak8997.instastylegallery.GalleryApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    UiBindingModule::class,
    SharedViewModelDelegateModule::class
])
internal interface AppComponent : AndroidInjector<GalleryApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
